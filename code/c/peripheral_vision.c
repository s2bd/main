#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <libusb-1.0/libusb.h>
#include <SDL2/SDL.h>
#include <SDL2/SDL_ttf.h>

#define WINDOW_WIDTH 600
#define WINDOW_HEIGHT 800
#define FONT_SIZE 18
#define MAX_VISIBLE_DEVICES 15 // Maximum number of devices visible at once

// Define colors
SDL_Color COLOR_RESET = {255, 255, 255, 255}; // White color
SDL_Color COLOR_GREY = {169, 169, 169, 255};  // Grey color
SDL_Color COLOR_BRIGHT = {0, 255, 0, 255};    // Bright green color
SDL_Color COLOR_BUTTON = {100, 100, 100, 255}; // Button color

int scan_devices(libusb_context *ctx, libusb_device ***devs) {
    ssize_t cnt = libusb_get_device_list(ctx, devs);
    return cnt;
}

void render_text(SDL_Renderer *renderer, TTF_Font *font, const char *text, int x, int y, SDL_Color color) {
    SDL_Surface *surface = TTF_RenderText_Solid(font, text, color);
    SDL_Texture *texture = SDL_CreateTextureFromSurface(renderer, surface);

    SDL_Rect text_rect = {x, y, surface->w, surface->h};
    SDL_RenderCopy(renderer, texture, NULL, &text_rect);

    SDL_FreeSurface(surface);
    SDL_DestroyTexture(texture);
}

void render_button(SDL_Renderer *renderer, const char *text, int x, int y, int width, int height, int hovered) {
    SDL_Rect button_rect = {x, y, width, height};
    SDL_SetRenderDrawColor(renderer, hovered ? 150 : COLOR_BUTTON.r, hovered ? 150 : COLOR_BUTTON.g, hovered ? 150 : COLOR_BUTTON.b, 255);
    SDL_RenderFillRect(renderer, &button_rect);

    // Render the button text
    SDL_Surface *surface = TTF_RenderText_Solid(TTF_OpenFont("font.ttf", FONT_SIZE), text, COLOR_RESET);
    SDL_Texture *texture = SDL_CreateTextureFromSurface(renderer, surface);

    SDL_Rect text_rect = {x + (width - surface->w) / 2, y + (height - surface->h) / 2, surface->w, surface->h};
    SDL_RenderCopy(renderer, texture, NULL, &text_rect);

    SDL_FreeSurface(surface);
    SDL_DestroyTexture(texture);
}

void print_device_info(struct libusb_device *dev, SDL_Renderer *renderer, TTF_Font *font, int *y_offset, int scroll_offset) {
    struct libusb_device_descriptor desc;
    int r = libusb_get_device_descriptor(dev, &desc);
    if (r < 0) {
        return;
    }

    char info[512];
    snprintf(info, sizeof(info), "Vendor ID: %04X, Product ID: %04X", desc.idVendor, desc.idProduct);
    
    if (*y_offset - scroll_offset >= 50) { // Ensure we're within the visible area
        render_text(renderer, font, info, 10, *y_offset - scroll_offset, COLOR_BRIGHT);
    }
    *y_offset += FONT_SIZE + 5;

    if (desc.iManufacturer || desc.iProduct || desc.iSerialNumber) {
        libusb_device_handle *handle;
        if (libusb_open(dev, &handle) == 0) {
            unsigned char buffer[256];

            if (desc.iManufacturer) {
                r = libusb_get_string_descriptor_ascii(handle, desc.iManufacturer, buffer, sizeof(buffer));
                if (r > 0 && *y_offset - scroll_offset >= 50) {
                    render_text(renderer, font, "Manufacturer: ", 10, *y_offset - scroll_offset, COLOR_GREY);
                    render_text(renderer, font, (char *)buffer, 10 + strlen("Manufacturer: ") * FONT_SIZE / 2, *y_offset - scroll_offset, COLOR_RESET);
                    *y_offset += FONT_SIZE + 5;
                }
            }

            if (desc.iProduct) {
                r = libusb_get_string_descriptor_ascii(handle, desc.iProduct, buffer, sizeof(buffer));
                if (r > 0 && *y_offset - scroll_offset >= 50) {
                    render_text(renderer, font, "Product: ", 10, *y_offset - scroll_offset, COLOR_GREY);
                    render_text(renderer, font, (char *)buffer, 10 + strlen("Product: ") * FONT_SIZE / 2, *y_offset - scroll_offset, COLOR_RESET);
                    *y_offset += FONT_SIZE + 5;
                }
            }

            if (desc.iSerialNumber) {
                r = libusb_get_string_descriptor_ascii(handle, desc.iSerialNumber, buffer, sizeof(buffer));
                if (r > 0 && *y_offset - scroll_offset >= 50) {
                    render_text(renderer, font, "Serial Number: ", 10, *y_offset - scroll_offset, COLOR_GREY);
                    render_text(renderer, font, (char *)buffer, 10 + strlen("Serial Number: ") * FONT_SIZE / 2, *y_offset - scroll_offset, COLOR_RESET);
                    *y_offset += FONT_SIZE + 5;
                }
            }

            libusb_close(handle);
        }
    }
}

int main(void) {
    libusb_context *ctx = NULL;
    if (libusb_init(&ctx) < 0) {
        fprintf(stderr, "Failed to initialize libusb\n");
        return EXIT_FAILURE;
    }

    libusb_device **devs;
    ssize_t cnt = scan_devices(ctx, &devs);

    // Initialize SDL
    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
        fprintf(stderr, "SDL could not initialize! SDL_Error: %s\n", SDL_GetError());
        libusb_free_device_list(devs, 1);
        libusb_exit(ctx);
        return EXIT_FAILURE;
    }

    if (TTF_Init() == -1) {
        fprintf(stderr, "SDL_ttf could not initialize! TTF_Error: %s\n", TTF_GetError());
        SDL_Quit();
        libusb_free_device_list(devs, 1);
        libusb_exit(ctx);
        return EXIT_FAILURE;
    }

    SDL_Window *window = SDL_CreateWindow("Peripheral Vision", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, WINDOW_WIDTH, WINDOW_HEIGHT, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
    if (!window) {
        fprintf(stderr, "Window could not be created! SDL_Error: %s\n", SDL_GetError());
        TTF_Quit();
        SDL_Quit();
        libusb_free_device_list(devs, 1);
        libusb_exit(ctx);
        return EXIT_FAILURE;
    }

    SDL_Renderer *renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
    if (!renderer) {
        fprintf(stderr, "Renderer could not be created! SDL_Error: %s\n", SDL_GetError());
        SDL_DestroyWindow(window);
        TTF_Quit();
        SDL_Quit();
        libusb_free_device_list(devs, 1);
        libusb_exit(ctx);
        return EXIT_FAILURE;
    }

    TTF_Font *font = TTF_OpenFont("font.ttf", FONT_SIZE);
    if (!font) {
        fprintf(stderr, "Failed to load font! TTF_Error: %s\n", TTF_GetError());
        SDL_DestroyRenderer(renderer);
        SDL_DestroyWindow(window);
        TTF_Quit();
        SDL_Quit();
        libusb_free_device_list(devs, 1);
        libusb_exit(ctx);
        return EXIT_FAILURE;
    }

    // Main loop
    int running = 1;
    SDL_Event event;
    int y_offset = 50; // Start a bit lower for better aesthetics
    int scroll_offset = 0; // Track scrolling offset

    while (running) {
        // Clear the renderer
        SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255); // Black background
        SDL_RenderClear(renderer);

        // Render Rescan Button
        int button_x = 10;
        int button_y = 10;
        int button_width = 100;
        int button_height = 30;
        int hovered = 0;

        // Check if the mouse is hovering over the button
        int mouse_x, mouse_y;
        SDL_GetMouseState(&mouse_x, &mouse_y);
        if (mouse_x >= button_x && mouse_x <= button_x + button_width && mouse_y >= button_y && mouse_y <= button_y + button_height) {
            hovered = 1;
        }

        // Render the button
        render_button(renderer, "Rescan", button_x, button_y, button_width, button_height, hovered);

        // Handle events
while (SDL_PollEvent(&event)) {
    if (event.type == SDL_QUIT) {
        running = 0;
    } else if (event.type == SDL_MOUSEBUTTONDOWN && event.button.button == SDL_BUTTON_LEFT) {
        // Check if the click is within the button bounds
        if (hovered) {
            y_offset = 50; // Reset y_offset to 50 for fresh start
            cnt = scan_devices(ctx, &devs);
        }
    } else if (event.type == SDL_KEYDOWN) {
        // Arrow keys for scrolling
        if (event.key.keysym.sym == SDLK_DOWN) {
            scroll_offset += 20; // Scroll down
            if (scroll_offset > (y_offset - WINDOW_HEIGHT + 50)) {
                scroll_offset = y_offset - WINDOW_HEIGHT + 50; // Prevent scrolling past the bottom
            }
        } else if (event.key.keysym.sym == SDLK_UP) {
            scroll_offset -= 20; // Scroll up
            if (scroll_offset < 0) {
                scroll_offset = 0; // Prevent scrolling past the top
            }
        }
    }
}

        // Reset y_offset after rescanning
        if (y_offset != 50) {
            y_offset = 50; // Reset to top of screen
        }

        // Print the device info with the scroll offset
        for (ssize_t i = 0; i < cnt; i++) {
            print_device_info(devs[i], renderer, font, &y_offset, scroll_offset);
        }

        // Present the renderer
        SDL_RenderPresent(renderer);
    }

    // Cleanup
    TTF_CloseFont(font);
    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    TTF_Quit();
    SDL_Quit();
    libusb_free_device_list(devs, 1);
    libusb_exit(ctx);

    return EXIT_SUCCESS;
}
