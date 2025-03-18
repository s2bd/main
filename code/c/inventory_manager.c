#include <gtk/gtk.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_ITEMS 100

typedef struct {
    char name[50];
    int quantity;
} Item;

Item inventory[MAX_ITEMS];
int item_count = 0;

GtkWidget *vbox;
GtkWidget *grid;
GtkWidget *search_entry;
GtkWidget *name_entry;
GtkWidget *quantity_entry;

void create_grid();
void add_item();
void delete_item(GtkWidget *widget, gpointer data);
void search_item();
void save_inventory();
void load_inventory();
void on_exit_button(GtkWidget *widget, gpointer data);

void create_grid() {
    if (grid) {
        gtk_widget_destroy(grid);
    }

    grid = gtk_grid_new();
    gtk_grid_set_row_homogeneous(GTK_GRID(grid), TRUE);

    gtk_grid_attach(GTK_GRID(grid), gtk_label_new("Item Name"), 0, 0, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), gtk_label_new("Quantity"), 1, 0, 1, 1);
    gtk_grid_attach(GTK_GRID(grid), gtk_label_new("Action"), 2, 0, 1, 1);

    for (int i = 0; i < item_count; i++) {
        gtk_grid_attach(GTK_GRID(grid), gtk_label_new(inventory[i].name), 0, i + 1, 1, 1);
        gtk_grid_attach(GTK_GRID(grid), gtk_label_new(g_strdup_printf("%d", inventory[i].quantity)), 1, i + 1, 1, 1);
        
        GtkWidget *delete_button = gtk_button_new_with_label("Delete");
        g_signal_connect(delete_button, "clicked", G_CALLBACK(delete_item), GINT_TO_POINTER(i));
        gtk_grid_attach(GTK_GRID(grid), delete_button, 2, i + 1, 1, 1);
    }

    gtk_box_pack_start(GTK_BOX(vbox), grid, TRUE, TRUE, 0);
    gtk_widget_show_all(vbox);
}

void add_item() {
    const char *name = gtk_entry_get_text(GTK_ENTRY(name_entry));
    const char *quantity_str = gtk_entry_get_text(GTK_ENTRY(quantity_entry));
    int quantity = atoi(quantity_str);

    if (quantity <= 0 || strlen(name) == 0) {
        // Show an error dialog if the input is invalid
        GtkWidget *dialog = gtk_message_dialog_new(GTK_WINDOW(gtk_widget_get_toplevel(name_entry)),
            GTK_DIALOG_DESTROY_WITH_PARENT, GTK_MESSAGE_ERROR, GTK_BUTTONS_OK,
            "Please enter a valid item name and quantity.");
        gtk_dialog_run(GTK_DIALOG(dialog));
        gtk_widget_destroy(dialog);
        return;
    }

    for (int i = 0; i < item_count; i++) {
        if (strcasecmp(inventory[i].name, name) == 0) {
            inventory[i].quantity += quantity;
            gtk_entry_set_text(GTK_ENTRY(quantity_entry), "1"); // Reset quantity entry
            create_grid();
            return;
        }
    }

    strcpy(inventory[item_count].name, name);
    inventory[item_count].quantity = quantity;
    item_count++;
    gtk_entry_set_text(GTK_ENTRY(name_entry), ""); // Clear name entry
    gtk_entry_set_text(GTK_ENTRY(quantity_entry), "1"); // Reset quantity entry
    create_grid();
}

void delete_item(GtkWidget *widget, gpointer data) {
    int index = GPOINTER_TO_INT(data);
    for (int i = index; i < item_count - 1; i++) {
        inventory[i] = inventory[i + 1];
    }
    item_count--;
    create_grid();
}

void search_item() {
    const char *search_text = gtk_entry_get_text(GTK_ENTRY(search_entry));
    for (int i = 0; i < item_count; i++) {
        if (strcasecmp(inventory[i].name, search_text) == 0) {
            // Optionally highlight or select the item in your UI
            break;
        }
    }
}

void save_inventory() {
    FILE *file = fopen("inventory.txt", "w");
    for (int i = 0; i < item_count; i++) {
        fprintf(file, "%s,%d\n", inventory[i].name, inventory[i].quantity);
    }
    fclose(file);
}

void load_inventory() {
    FILE *file = fopen("inventory.txt", "r");
    if (!file) return;

    item_count = 0;
    while (fscanf(file, "%49[^,],%d\n", inventory[item_count].name, &inventory[item_count].quantity) == 2) {
        item_count++;
    }
    fclose(file);
    create_grid();
}

void on_exit_button(GtkWidget *widget, gpointer data) {
    save_inventory(); // Save on exit
    gtk_main_quit();
}

int main(int argc, char *argv[]) {
    gtk_init(&argc, &argv);

    GtkWidget *window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Inventory Manager");
    gtk_window_set_default_size(GTK_WINDOW(window), 1280, 720);

    vbox = gtk_box_new(GTK_ORIENTATION_VERTICAL, 5);
    gtk_container_add(GTK_CONTAINER(window), vbox);

    GtkWidget *menu_bar = gtk_menu_bar_new();
    GtkWidget *file_menu = gtk_menu_new();
    GtkWidget *file_item = gtk_menu_item_new_with_label("File");
    GtkWidget *load_item = gtk_menu_item_new_with_label("Load Inventory");
    GtkWidget *save_item = gtk_menu_item_new_with_label("Save Inventory");
    GtkWidget *exit_item = gtk_menu_item_new_with_label("Exit");

    g_signal_connect(load_item, "activate", G_CALLBACK(load_inventory), NULL);
    g_signal_connect(save_item, "activate", G_CALLBACK(save_inventory), NULL);
    g_signal_connect(exit_item, "activate", G_CALLBACK(on_exit_button), NULL);

    gtk_menu_shell_append(GTK_MENU_SHELL(file_menu), load_item);
    gtk_menu_shell_append(GTK_MENU_SHELL(file_menu), save_item);
    gtk_menu_shell_append(GTK_MENU_SHELL(file_menu), exit_item);
    gtk_menu_shell_append(GTK_MENU_SHELL(menu_bar), file_item);
    gtk_menu_item_set_submenu(GTK_MENU_ITEM(file_item), file_menu);

    gtk_box_pack_start(GTK_BOX(vbox), menu_bar, FALSE, FALSE, 0);

    search_entry = gtk_entry_new();
    gtk_box_pack_start(GTK_BOX(vbox), search_entry, FALSE, FALSE, 0);
    g_signal_connect(search_entry, "changed", G_CALLBACK(search_item), NULL);

    name_entry = gtk_entry_new();
    gtk_box_pack_start(GTK_BOX(vbox), name_entry, FALSE, FALSE, 0);

    quantity_entry = gtk_entry_new();
    gtk_entry_set_text(GTK_ENTRY(quantity_entry), "1");
    gtk_box_pack_start(GTK_BOX(vbox), quantity_entry, FALSE, FALSE, 0);

    GtkWidget *add_button = gtk_button_new_with_label("Add Item");
    g_signal_connect(add_button, "clicked", G_CALLBACK(add_item), NULL);
    gtk_box_pack_start(GTK_BOX(vbox), add_button, FALSE, FALSE, 0);

    load_inventory(); // Load inventory on startup

    g_signal_connect(window, "destroy", G_CALLBACK(on_exit_button), NULL);
    gtk_widget_show_all(window);
    gtk_main();

    return 0;
}
