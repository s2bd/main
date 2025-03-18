#include <gtk/gtk.h>
#include <stdlib.h>
#include <string.h>

#define GRID_WIDTH 80
#define GRID_HEIGHT 60
#define CELL_SIZE 10

typedef struct {
    int grid[GRID_WIDTH][GRID_HEIGHT];
    int is_playing;
    char custom_rule[100];
} CellularAutomata;

CellularAutomata automata;

void toggle_cell(int x, int y) {
    automata.grid[x][y] = !automata.grid[x][y]; // Toggle cell state
}

void update_grid() {
    int new_grid[GRID_WIDTH][GRID_HEIGHT] = {0};

    for (int x = 0; x < GRID_WIDTH; x++) {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            int alive_neighbors = 0;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    int nx = x + dx, ny = y + dy;
                    if (nx >= 0 && nx < GRID_WIDTH && ny >= 0 && ny < GRID_HEIGHT) {
                        alive_neighbors += automata.grid[nx][ny];
                    }
                }
            }
            // Example rule: Conway's Game of Life
            if (automata.grid[x][y] == 1) {
                new_grid[x][y] = (alive_neighbors == 2 || alive_neighbors == 3) ? 1 : 0;
            } else {
                new_grid[x][y] = (alive_neighbors == 3) ? 1 : 0;
            }
        }
    }
    memcpy(automata.grid, new_grid, sizeof(automata.grid));
}

gboolean draw_callback(GtkWidget *widget, cairo_t *cr, gpointer data) {
    for (int x = 0; x < GRID_WIDTH; x++) {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            if (automata.grid[x][y] == 1) {
                cairo_rectangle(cr, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                cairo_set_source_rgb(cr, 1, 1, 1); // White for alive cells
                cairo_fill(cr);
            }
        }
    }
    return FALSE;
}

void mouse_click_callback(GtkWidget *widget, GdkEventButton *event) {
    if (event->button == GDK_BUTTON_PRIMARY) {
        int x = event->x / CELL_SIZE;
        int y = event->y / CELL_SIZE;
        if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT) {
            toggle_cell(x, y);
            gtk_widget_queue_draw(widget);
        }
    }
}

void play_callback(GtkWidget *widget) {
    automata.is_playing = 1;
}

void pause_callback(GtkWidget *widget) {
    automata.is_playing = 0;
}

void reset_callback(GtkWidget *widget) {
    memset(automata.grid, 0, sizeof(automata.grid));
    gtk_widget_queue_draw(widget);
}

void update_callback(GtkWidget *widget) {
    if (automata.is_playing) {
        update_grid();
        gtk_widget_queue_draw(widget);
    }
}

void custom_rule_callback(GtkWidget *widget) {
    const gchar *rule = gtk_entry_get_text(GTK_ENTRY(widget));
    strncpy(automata.custom_rule, rule, sizeof(automata.custom_rule) - 1);
    automata.custom_rule[sizeof(automata.custom_rule) - 1] = '\0';

    // Here you would typically parse the rule and apply it to the simulation
}

int main(int argc, char *argv[]) {
    gtk_init(&argc, &argv);
    automata.is_playing = 0;
    memset(automata.grid, 0, sizeof(automata.grid));

    GtkWidget *window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    GtkWidget *drawing_area = gtk_drawing_area_new();
    GtkWidget *play_button = gtk_button_new_with_label("Play");
    GtkWidget *pause_button = gtk_button_new_with_label("Pause");
    GtkWidget *reset_button = gtk_button_new_with_label("Reset");
    GtkWidget *custom_rule_entry = gtk_entry_new();

    gtk_widget_set_size_request(drawing_area, GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);

    GtkWidget *box = gtk_box_new(GTK_ORIENTATION_VERTICAL, 5);
    gtk_box_pack_start(GTK_BOX(box), drawing_area, TRUE, TRUE, 0);
    gtk_box_pack_start(GTK_BOX(box), play_button, FALSE, FALSE, 0);
    gtk_box_pack_start(GTK_BOX(box), pause_button, FALSE, FALSE, 0);
    gtk_box_pack_start(GTK_BOX(box), reset_button, FALSE, FALSE, 0);
    gtk_box_pack_start(GTK_BOX(box), custom_rule_entry, FALSE, FALSE, 0);
    gtk_container_add(GTK_CONTAINER(window), box);

    g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);
    g_signal_connect(drawing_area, "draw", G_CALLBACK(draw_callback), NULL);
    g_signal_connect(drawing_area, "button-press-event", G_CALLBACK(mouse_click_callback), NULL);
    g_signal_connect(play_button, "clicked", G_CALLBACK(play_callback), NULL);
    g_signal_connect(pause_button, "clicked", G_CALLBACK(pause_callback), NULL);
    g_signal_connect(reset_button, "clicked", G_CALLBACK(reset_callback), NULL);
    g_signal_connect(custom_rule_entry, "activate", G_CALLBACK(custom_rule_callback), NULL);

    gtk_widget_set_events(drawing_area, gtk_widget_get_events(drawing_area) | GDK_BUTTON_PRESS_MASK);
    g_timeout_add(100, (GSourceFunc)update_callback, drawing_area);

    gtk_widget_show_all(window);
    gtk_main();

    return 0;
}
