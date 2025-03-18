#include <gtk/gtk.h>
#include <stdlib.h>

static void start_recording(GtkWidget *widget, gpointer data) {
    // Output filename
    const char *outputFilename = (const char *)data;

    // Command to start recording
    char startCmd[512];
    snprintf(startCmd, 512, "ffmpeg -f x11grab -r 25 -s 1920x1080 -i :0.0 -f alsa -i default -c:v libx264 -preset ultrafast -c:a aac %s", outputFilename);

    // Start recording
    system(startCmd);
}

static void stop_recording(GtkWidget *widget, gpointer data) {
    // Command to stop recording
    const char *stopCmd = "pkill ffmpeg";

    // Stop recording
    system(stopCmd);
}

int main(int argc, char *argv[]) {
    GtkWidget *window;
    GtkWidget *startButton;
    GtkWidget *stopButton;
    const char *outputFilename;

    // Initialize GTK
    gtk_init(&argc, &argv);

    // Check for proper usage
    if (argc != 2) {
        g_print("Usage: %s <output_filename>\n", argv[0]);
        return 1;
    }

    // Output filename
    outputFilename = argv[1];

    // Create the main window
    window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Screen Recorder");
    gtk_window_set_default_size(GTK_WINDOW(window), 200, 100);
    g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);

    // Create start button
    startButton = gtk_button_new_with_label("Start Recording");
    g_signal_connect(startButton, "clicked", G_CALLBACK(start_recording), (gpointer)outputFilename);

    // Create stop button
    stopButton = gtk_button_new_with_label("Stop Recording");
    g_signal_connect(stopButton, "clicked", G_CALLBACK(stop_recording), NULL);

    // Add buttons to the window
    GtkWidget *box = gtk_box_new(GTK_ORIENTATION_VERTICAL, 5);
    gtk_box_pack_start(GTK_BOX(box), startButton, TRUE, TRUE, 0);
    gtk_box_pack_start(GTK_BOX(box), stopButton, TRUE, TRUE, 0);
    gtk_container_add(GTK_CONTAINER(window), box);

    // Show all widgets
    gtk_widget_show_all(window);

    // Run the GTK main loop
    gtk_main();

    return 0;
}

