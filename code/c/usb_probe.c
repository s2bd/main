#include <stdio.h>
#include <libusb-1.0/libusb.h>

#define COLOR_RESET "\033[0m"
#define COLOR_GREY "\033[90m"
#define COLOR_BRIGHT "\033[1;32m" // Bright green for ASCII characters

void print_hex_ascii(const unsigned char *data, int length) {
    printf("  Hex:   ");
    for (int i = 0; i < length; i++) {
        printf("%02X ", data[i]);
    }
    printf("\n");

    printf("  ASCII: ");
    for (int i = 0; i < length; i++) {
        if (data[i] >= 32 && data[i] < 127) {
            printf(COLOR_BRIGHT "%c " COLOR_RESET, data[i]);
        } else {
            printf(". ");
        }
    }
    printf("\n");
}

void print_device_info(struct libusb_device *dev) {
    struct libusb_device_descriptor desc;
    int r = libusb_get_device_descriptor(dev, &desc);
    if (r < 0) {
        fprintf(stderr, "Failed to get device descriptor\n");
        return;
    }

    printf("Device Descriptor:\n");
    printf(COLOR_GREY "  Vendor ID: %04X\n" COLOR_RESET, desc.idVendor);
    printf(COLOR_GREY "  Product ID: %04X\n" COLOR_RESET, desc.idProduct);
    printf(COLOR_GREY "  Class: %02X\n" COLOR_RESET, desc.bDeviceClass);
    printf(COLOR_GREY "  Subclass: %02X\n" COLOR_RESET, desc.bDeviceSubClass);
    printf(COLOR_GREY "  Protocol: %02X\n" COLOR_RESET, desc.bDeviceProtocol);
    printf(COLOR_GREY "  Max Packet Size: %d\n" COLOR_RESET, desc.bMaxPacketSize0);
    printf(COLOR_GREY "  Number of Configurations: %d\n" COLOR_RESET, desc.bNumConfigurations);

    libusb_device_handle *handle;
    r = libusb_open(dev, &handle);
    if (r == 0) {
        unsigned char buffer[256];
        if (desc.iManufacturer) {
            r = libusb_get_string_descriptor_ascii(handle, desc.iManufacturer, buffer, sizeof(buffer));
            if (r >= 0) {
                printf(COLOR_GREY "  Manufacturer: " COLOR_RESET);
                print_hex_ascii(buffer, r);
            }
        }
        if (desc.iProduct) {
            r = libusb_get_string_descriptor_ascii(handle, desc.iProduct, buffer, sizeof(buffer));
            if (r >= 0) {
                printf(COLOR_GREY "  Product: " COLOR_RESET);
                print_hex_ascii(buffer, r);
            }
        }
        if (desc.iSerialNumber) {
            r = libusb_get_string_descriptor_ascii(handle, desc.iSerialNumber, buffer, sizeof(buffer));
            if (r >= 0) {
                printf(COLOR_GREY "  Serial Number: " COLOR_RESET);
                print_hex_ascii(buffer, r);
            }
        }
        libusb_close(handle);
    } else {
        fprintf(stderr, "Failed to open device: %s\n", libusb_error_name(r));
    }
}

// Check if a device has an interface with USB Mass Storage class (0x08)
int is_mass_storage_device(libusb_device *dev) {
    struct libusb_config_descriptor *config;
    int r = libusb_get_active_config_descriptor(dev, &config);
    if (r < 0) {
        fprintf(stderr, "Failed to get config descriptor\n");
        return 0;
    }

    for (int i = 0; i < config->bNumInterfaces; i++) {
        const struct libusb_interface *inter = &config->interface[i];
        for (int j = 0; j < inter->num_altsetting; j++) {
            const struct libusb_interface_descriptor *interdesc = &inter->altsetting[j];
            if (interdesc->bInterfaceClass == 0x08) {
                printf(COLOR_BRIGHT "Mass Storage Device detected\n" COLOR_RESET);
                libusb_free_config_descriptor(config);
                return 1;
            }
        }
    }

    libusb_free_config_descriptor(config);
    return 0;
}

void print_hdd_stats(libusb_device *dev) {
    struct libusb_device_descriptor desc;
    int r = libusb_get_device_descriptor(dev, &desc);
    if (r < 0) {
        fprintf(stderr, "Failed to get HDD descriptor\n");
        return;
    }

    printf(COLOR_BRIGHT "HDD Device Stats:\n" COLOR_RESET);
    printf(COLOR_GREY "  Vendor ID: %04X\n" COLOR_RESET, desc.idVendor);
    printf(COLOR_GREY "  Product ID: %04X\n" COLOR_RESET, desc.idProduct);
    printf(COLOR_GREY "  USB Speed: %u\n" COLOR_RESET, libusb_get_device_speed(dev));
    printf(COLOR_GREY "  Max Packet Size: %d\n" COLOR_RESET, desc.bMaxPacketSize0);

    // Get the active configuration descriptor for power-related details
    struct libusb_config_descriptor *config;
    r = libusb_get_active_config_descriptor(dev, &config);
    if (r == 0) {
        printf(COLOR_GREY "  Max Power Consumption: %d mA\n" COLOR_RESET, config->MaxPower * 2); // Multiply by 2 to get mA
        libusb_free_config_descriptor(config);
    } else {
        fprintf(stderr, "Failed to get configuration descriptor\n");
    }

    printf(COLOR_GREY "  Number of Configurations: %d\n" COLOR_RESET, desc.bNumConfigurations);
}

int main(void) {
    libusb_context *ctx = NULL;
    int r = libusb_init(&ctx);
    if (r < 0) {
        fprintf(stderr, "Failed to initialize libusb: %s\n", libusb_error_name(r));
        return 1;
    }

    libusb_device **devs;
    ssize_t cnt = libusb_get_device_list(ctx, &devs);
    if (cnt < 0) {
        fprintf(stderr, "Failed to get device list: %s\n", libusb_error_name((int)cnt));
        libusb_exit(ctx);
        return 1;
    }

    printf("Connected USB devices:\n");
    for (ssize_t i = 0; i < cnt; i++) {
        print_device_info(devs[i]);
        printf("\n");

        // Check if the device is a mass storage device (i.e., an external HDD)
        if (is_mass_storage_device(devs[i])) {
            print_hdd_stats(devs[i]); // Display HDD-specific stats
            printf("\n");
        }
    }

    libusb_free_device_list(devs, 1);
    libusb_exit(ctx);
    return 0;
}
