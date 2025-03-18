#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <pulse/simple.h>
#include <pulse/error.h>
#include <gtk/gtk.h>

GtkWidget *toggle_button;
volatile int sync_enabled = 0;

// Function to write to the keyboard backlight
void set_keyboard_brightness(int brightness) {
    FILE *f = fopen("/sys/class/leds/hp::kbd_backlight/brightness", "w");
    if (f != NULL) {
        fprintf(f, "%d", brightness);
        fclose(f);
    } else {
        perror("Failed to open backlight control");
    }
}

// Function to read audio volume using PulseAudio
int get_audio_volume() {
    static const pa_sample_spec ss = {
        .format = PA_SAMPLE_S16LE,
        .rate = 44100,
        .channels = 2
    };

    pa_simple *s = NULL;
    int error;
    uint8_t buf[BUFSIZE];

    // Connect to PulseAudio
    if (!(s = pa_simple_new(NULL, "VolumeMonitor", PA_STREAM_RECORD, NULL, "record", &ss, NULL, NULL, &error))) {
        fprintf(stderr, "pa_simple_new() failed: %s\n", pa_strerror(error));
        return -1;
    }

    // Read audio sample
    if (pa_simple_read(s, buf, sizeof(buf), &error) < 0) {
        fprintf(stderr, "pa_simple_read() failed: %s\n", pa_strerror(error));
        pa_simple_free(s);
        return -1;
    }

    // Calculate volume (simple version)
    int volume = 0;
    for (int i = 0; i < sizeof(buf); i++) {
        volume += abs(buf[i]);
    }
    volume = volume / sizeof(buf);  // Average out

    pa_simple_free(s);
    return volume;
}

// Toggle function for the GUI button
static void toggle_backlight(GtkWidget *widget, gpointer data) {
    sync_enabled = gtk_toggle_button_get_active(GTK_TOGGLE_BUTTON(widget));
}

// Reset backlight on program exit
void reset_backlight(int signum) {
    set_keyboard_brightness(1);  // Reset to default brightness
    exit(0);
}

int main(int argc, char *argv[]) {
    // Set up signal handling
    signal(SIGINT, reset_backlight);
    signal(SIGTERM, reset_backlight);

    gtk_init(&argc, &argv);

    GtkWidget *window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Keyboard Backlight Sync");
    g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);

    toggle_button = gtk_toggle_button_new_with_label("Enable Sync");
    g_signal_connect(toggle_button, "toggled", G_CALLBACK(toggle_backlight), NULL);
    gtk_container_add(GTK_CONTAINER(window), toggle_button);

    gtk_widget_show_all(window);

    // Main loop
    while (1) {
        if (sync_enabled) {
            int volume = get_audio_volume();
            int brightness = volume / 100;  // Scale to 0-2 or 0-255 depending on your setup
            set_keyboard_brightness(brightness);
        }
        usleep(100000);  // Delay to avoid high CPU usage
    }

    return 0;
}
