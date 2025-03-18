#include <gtk/gtk.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Callback function to execute the command
void execute_command(GtkWidget *widget, gpointer data) {
    GtkTextBuffer *buffer = GTK_TEXT_BUFFER(data);
    FILE *fp;
    char path[1035];
    char ssid[256];
    char password[256] = "None";

    GtkTextIter end;
    gtk_text_buffer_get_end_iter(buffer, &end);

    /* Open the command for reading. */
    fp = popen("nmcli -t -f NAME connection show", "r");
    if (fp == NULL) {
        gtk_text_buffer_insert(buffer, &end, "Failed to run command\n", -1);
        return;
    }

    /* Read the output a line at a time - output it. */
    while (fgets(path, sizeof(path), fp) != NULL) {
        // Remove newline
        path[strcspn(path, "\n")] = 0;
        strcpy(ssid, path);

        char command[512];
        sprintf(command, "nmcli -s -g 802-11-wireless-security.psk connection show \"%s\"", ssid);

        FILE *fp2 = popen(command, "r");
        if (fp2 != NULL) {
            if (fgets(password, sizeof(password), fp2) != NULL) {
                // Remove newline
                password[strcspn(password, "\n")] = 0;
            }
            pclose(fp2);
        }

        if (strlen(password) == 0) {
            strcpy(password, "None");
        }

        char output[1024];
        snprintf(output, sizeof(output), "SSID: %s\nPassword: %s\n\n", ssid, password);
        gtk_text_buffer_get_end_iter(buffer, &end);
        gtk_text_buffer_insert(buffer, &end, output, -1);
    }

    /* close */
    pclose(fp);
}

// Callback function to close the application
void on_destroy(GtkWidget *widget, gpointer data) {
    gtk_main_quit();
}

int main(int argc, char *argv[]) {
    GtkWidget *window;
    GtkWidget *button;
    GtkWidget *text_view;
    GtkWidget *vbox;
    GtkTextBuffer *buffer;

    gtk_init(&argc, &argv);

    // Create a new window
    window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Wifi Checker");
    gtk_window_set_default_size(GTK_WINDOW(window), 600, 450);

    // Connect the destroy signal to the callback
    g_signal_connect(window, "destroy", G_CALLBACK(on_destroy), NULL);

    // Create a vertical box container
    vbox = gtk_box_new(GTK_ORIENTATION_VERTICAL, 5);
    gtk_container_add(GTK_CONTAINER(window), vbox);

    // Create a new button
    button = gtk_button_new_with_label("Rescan");
    gtk_box_pack_start(GTK_BOX(vbox), button, FALSE, FALSE, 5);

    // Create a new text view
    text_view = gtk_text_view_new();
    buffer = gtk_text_view_get_buffer(GTK_TEXT_VIEW(text_view));
    gtk_text_view_set_editable(GTK_TEXT_VIEW(text_view), FALSE);
    gtk_text_view_set_cursor_visible(GTK_TEXT_VIEW(text_view), FALSE);
    gtk_box_pack_start(GTK_BOX(vbox), text_view, TRUE, TRUE, 5);

    // Connect the button click event to the callback
    g_signal_connect(button, "clicked", G_CALLBACK(execute_command), buffer);

    // Show all widgets in the window
    gtk_widget_show_all(window);

    // Start the GTK main loop
    gtk_main();

    return 0;
}

