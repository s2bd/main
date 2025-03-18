#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <gtk/gtk.h>

// Define a structure to represent a product
typedef struct {
    char name[50];
    int quantity;
    float price;
    char category[50];
} Product;

// Function to add a product to the inventory and store it in a file
void add_product(GtkWidget *widget, gpointer data) {
    // Retrieve the entry field values
    GtkEntry *name_entry = GTK_ENTRY(data);
    GtkEntry *quantity_entry = GTK_ENTRY(g_object_get_data(G_OBJECT(data), "quantity"));
    GtkEntry *price_entry = GTK_ENTRY(g_object_get_data(G_OBJECT(data), "price"));
    GtkEntry *category_entry = GTK_ENTRY(g_object_get_data(G_OBJECT(data), "category"));

    const gchar *name = gtk_entry_get_text(name_entry);
    gint quantity = atoi(gtk_entry_get_text(quantity_entry));
    gfloat price = atof(gtk_entry_get_text(price_entry));
    const gchar *category = gtk_entry_get_text(category_entry);

    // Create a product object
    Product new_product;
    strcpy(new_product.name, name);
    new_product.quantity = quantity;
    new_product.price = price;
    strcpy(new_product.category, category);

    // Open the file in append mode
    FILE *file = fopen("inventory.csv", "a");
    if (file == NULL) {
        g_print("Error opening file.\n");
        return;
    }

    // Write the product details to the file
    fprintf(file, "%s,%d,%.2f,%s\n", new_product.name, new_product.quantity,
            new_product.price, new_product.category);

    // Close the file
    fclose(file);

    // Display a message dialog
    GtkWidget *dialog = gtk_message_dialog_new(NULL, GTK_DIALOG_MODAL,
                                                GTK_MESSAGE_INFO, GTK_BUTTONS_OK,
                                                "Product added successfully!");
    gtk_dialog_run(GTK_DIALOG(dialog));
    gtk_widget_destroy(dialog);
}

// Function to display the inventory database
void view_database(GtkWidget *widget, gpointer data) {
    // Create a new window to display the database
    GtkWidget *database_window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(database_window), "Inventory Database");
    gtk_window_set_default_size(GTK_WINDOW(database_window), 400, 300);
    g_signal_connect(database_window, "destroy", G_CALLBACK(gtk_widget_destroy), NULL);

    // Create a text view to display the database contents
    GtkWidget *text_view = gtk_text_view_new();
    gtk_text_view_set_editable(GTK_TEXT_VIEW(text_view), FALSE);
    gtk_text_view_set_cursor_visible(GTK_TEXT_VIEW(text_view), FALSE);
    gtk_container_add(GTK_CONTAINER(database_window), text_view);

    // Load the inventory data from the file and display it in the text view
    FILE *file = fopen("inventory.csv", "r");
    if (file != NULL) {
        GtkTextBuffer *buffer = gtk_text_view_get_buffer(GTK_TEXT_VIEW(text_view));
        gchar line[256];
        while (fgets(line, sizeof(line), file) != NULL) {
            gtk_text_buffer_insert_at_cursor(buffer, line, -1);
        }
        fclose(file);
    } else {
        g_print("Error opening file.\n");
    }

    // Show the database window
    gtk_widget_show_all(database_window);
}

int main(int argc, char *argv[]) {
    gtk_init(&argc, &argv);

    // Create the main window
    GtkWidget *window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Fancy Inventory Management System");
    gtk_window_set_default_size(GTK_WINDOW(window), 400, 300);
    g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);

    // Create a grid layout
    GtkWidget *grid = gtk_grid_new();
    gtk_container_add(GTK_CONTAINER(window), grid);

    // Create entry fields and labels for product details
    GtkWidget *name_label = gtk_label_new("Name:");
    GtkWidget *name_entry = gtk_entry_new();
    GtkWidget *quantity_label = gtk_label_new("Quantity:");
    GtkWidget *quantity_entry = gtk_entry_new();
    GtkWidget *price_label = gtk_label_new("Price:");
    GtkWidget *price_entry = gtk_entry_new();
    GtkWidget *category_label = gtk_label_new("Category:");
    GtkWidget *category_entry = gtk_entry_new();

    // Add widgets to the grid layout
    gtk_grid_attach(GTK_GRID(grid), name_label, 0, 0, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), name_entry, 1, 0, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), quantity_label, 0, 1, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), quantity_entry, 1, 1, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), price_label, 0, 2, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), price_entry, 1, 2, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), category_label, 0, 3, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), category_entry, 1, 3, 1, 1);

    // Create "Add Product" button
    GtkWidget *add_button = gtk_button_new_with_label("Add Product");
    g_signal_connect(add_button, "clicked", G_CALLBACK(add_product), name_entry);
    g_object_set_data(G_OBJECT(name_entry), "quantity", quantity_entry);
    g_object_set_data(G_OBJECT(name_entry), "price", price_entry);
    g_object_set_data(G_OBJECT(name_entry), "category", category_entry);

    // Add "View Database" button
    GtkWidget *view_button = gtk_button_new_with_label("View Database");
    g_signal_connect(view_button, "clicked", G_CALLBACK(view_database), NULL);

    // Add buttons to the grid layout
    gtk_grid_attach(GTK_GRID(grid), add_button, 0, 4, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), view_button, 1, 4, 1, 1);

    // Show all widgets
    gtk_widget_show_all(window);

    // Run the GTK main loop
    gtk_main();

    return 0;
}


