#include <gtk/gtk.h>
#include <math.h>

GtkWidget *entry;

void calculate(GtkWidget *widget, gpointer data) {
    const gchar *input = gtk_entry_get_text(GTK_ENTRY(entry));
    double result = 0.0;
    char operation[10];
    double value;
    sscanf(input, "%lf %s", &value, operation);

    if (strcmp(operation, "sqrt") == 0) {
        result = sqrt(value);
    } else if (strcmp(operation, "log") == 0) {
        result = log(value);
    } else if (strcmp(operation, "sin") == 0) {
        result = sin(value);
    } else if (strcmp(operation, "cos") == 0) {
        result = cos(value);
    } else if (strcmp(operation, "tan") == 0) {
        result = tan(value);
    }

    char result_str[50];
    sprintf(result_str, "Result: %.2f", result);
    gtk_entry_set_text(GTK_ENTRY(entry), result_str);
}

int main(int argc, char *argv[]) {
    gtk_init(&argc, &argv);

    GtkWidget *window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Scientific Calculator");
    gtk_window_set_default_size(GTK_WINDOW(window), 300, 200);

    entry = gtk_entry_new();
    GtkWidget *button = gtk_button_new_with_label("Calculate");
    g_signal_connect(button, "clicked", G_CALLBACK(calculate), NULL);

    GtkWidget *box = gtk_box_new(GTK_ORIENTATION_VERTICAL, 5);
    gtk_box_pack_start(GTK_BOX(box), entry, FALSE, FALSE, 5);
    gtk_box_pack_start(GTK_BOX(box), button, FALSE, FALSE, 5);
    gtk_container_add(GTK_CONTAINER(window), box);

    g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);
    gtk_widget_show_all(window);
    gtk_main();

    return 0;
}
