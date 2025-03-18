import gi
gi.require_version("Gtk", "3.0")
from gi.repository import Gtk, Gio, GLib
import os
import subprocess
import threading
import sys

class PythonIDE(Gtk.Application):
    def __init__(self):
        super().__init__(application_id="org.example.pythonide")
        self.connect("activate", self.on_activate)

    def on_activate(self, app):
        self.window = Gtk.ApplicationWindow(application=app)
        self.window.set_title("Monty Python IDE")
        self.window.set_default_size(1000, 600)

        self.file_path = None
        self.init_ui()

        self.window.show_all()

    def init_ui(self):
        # Create Header Bar with Menu
        header_bar = Gtk.HeaderBar()
        header_bar.set_show_close_button(True)
        header_bar.props.title = "Monty Python IDE"
        self.window.set_titlebar(header_bar)

        # Create Menu Button
        menu_button = Gtk.MenuButton()
        menu_icon = Gio.ThemedIcon(name="open-menu-symbolic")
        menu_image = Gtk.Image.new_from_gicon(menu_icon, Gtk.IconSize.BUTTON)
        menu_button.add(menu_image)

        # Create Menu
        menu = Gio.Menu()
        menu.append("New", "app.new")
        menu.append("Open", "app.open")
        menu.append("Save", "app.save")
        menu.append("Save As", "app.saveas")
        menu.append("Exit", "app.exit")
        menu.append("Run", "app.run")

        menu_button.set_menu_model(menu)
        header_bar.pack_end(menu_button)

        # Create Paned Window
        paned = Gtk.Paned(orientation=Gtk.Orientation.HORIZONTAL)
        self.window.add(paned)

        # Create File Explorer
        self.file_explorer = Gtk.TreeView()
        self.file_explorer.set_size_request(200, -1)
        paned.pack1(self.file_explorer, resize=True, shrink=True)

        self.create_file_explorer()

        # Create Text Editor
        text_editor_scrolled = Gtk.ScrolledWindow()
        self.text_editor = Gtk.TextView()
        text_editor_scrolled.add(self.text_editor)
        paned.pack2(text_editor_scrolled, resize=True, shrink=True)

        # Create Console
        self.console = Gtk.TextView()
        self.console.set_editable(False)
        console_scrolled = Gtk.ScrolledWindow()
        console_scrolled.set_size_request(-1, 150)
        console_scrolled.add(self.console)
        paned.pack2(console_scrolled, resize=False, shrink=False)

        self.populate_file_explorer()

        # Add Actions to the Application
        action = Gio.SimpleAction.new("new", None)
        action.connect("activate", self.new_file)
        self.add_action(action)

        action = Gio.SimpleAction.new("open", None)
        action.connect("activate", self.open_file_dialog)
        self.add_action(action)

        action = Gio.SimpleAction.new("save", None)
        action.connect("activate", self.save_file)
        self.add_action(action)

        action = Gio.SimpleAction.new("saveas", None)
        action.connect("activate", self.save_as_file_dialog)
        self.add_action(action)

        action = Gio.SimpleAction.new("exit", None)
        action.connect("activate", self.exit_app)
        self.add_action(action)

        action = Gio.SimpleAction.new("run", None)
        action.connect("activate", self.run_script)
        self.add_action(action)

    def create_file_explorer(self):
        renderer = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("File Explorer", renderer, text=0)
        self.file_explorer.append_column(column)

        self.file_explorer_store = Gtk.ListStore(str, str)
        self.file_explorer.set_model(self.file_explorer_store)
        self.file_explorer.connect("row-activated", self.on_file_open)

    def populate_file_explorer(self):
        path = os.getcwd()
        for root, dirs, files in os.walk(path):
            for directory in dirs:
                self.file_explorer_store.append([directory, os.path.join(root, directory)])
            for file in files:
                self.file_explorer_store.append([file, os.path.join(root, file)])
            break

    def on_file_open(self, tree_view, path, column):
        model = tree_view.get_model()
        tree_iter = model.get_iter(path)
        self.file_path = model.get_value(tree_iter, 1)
        self.open_file()

    def new_file(self, action, parameter):
        self.text_editor.get_buffer().set_text("")
        self.file_path = None

    def open_file_dialog(self, action, parameter):
        dialog = Gtk.FileChooserDialog(
            title="Open File",
            parent=self.window,
            action=Gtk.FileChooserAction.OPEN,
            buttons=(
                Gtk.STOCK_CANCEL,
                Gtk.ResponseType.CANCEL,
                Gtk.STOCK_OPEN,
                Gtk.ResponseType.ACCEPT
            )
        )

        dialog.set_default_response(Gtk.ResponseType.ACCEPT)
        dialog.set_current_folder(os.getcwd())

        if dialog.run() == Gtk.ResponseType.ACCEPT:
            self.file_path = dialog.get_filename()
            self.open_file()

        dialog.destroy()

    def open_file(self):
        if self.file_path:
            try:
                with open(self.file_path, "r", encoding="utf-8") as file:
                    buffer = self.text_editor.get_buffer()
                    buffer.set_text(file.read())
            except Exception as e:
                self.show_error(str(e))

    def save_file(self, action=None, parameter=None):
        if self.file_path:
            content = self.get_text_editor_content()
            with open(self.file_path, "w", encoding="utf-8") as file:
                file.write(content)
        else:
            self.save_as_file_dialog(action, parameter)

    def save_as_file_dialog(self, action, parameter):
        dialog = Gtk.FileChooserDialog(
            title="Save File",
            parent=self.window,
            action=Gtk.FileChooserAction.SAVE,
            buttons=(
                Gtk.STOCK_CANCEL,
                Gtk.ResponseType.CANCEL,
                Gtk.STOCK_SAVE,
                Gtk.ResponseType.ACCEPT
            )
        )

        dialog.set_default_response(Gtk.ResponseType.ACCEPT)
        dialog.set_current_folder(os.getcwd())
        dialog.set_do_overwrite_confirmation(True)

        if dialog.run() == Gtk.ResponseType.ACCEPT:
            self.file_path = dialog.get_filename()
            self.save_file()

        dialog.destroy()

    def exit_app(self, action, parameter):
        self.quit()

    def run_script(self, action=None, parameter=None):
        if not self.file_path:
            self.show_error("Please save the file before running.")
            return

        self.save_file()
        self.clear_console()

        def run_in_thread():
            process = subprocess.Popen([sys.executable, self.file_path], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            stdout, stderr = process.communicate()

            GLib.idle_add(self.update_console, stdout, stderr)

        threading.Thread(target=run_in_thread).start()

    def update_console(self, stdout, stderr):
        buffer = self.console.get_buffer()
        buffer.insert(buffer.get_end_iter(), stdout)
        if stderr:
            buffer.insert(buffer.get_end_iter(), stderr)

    def clear_console(self):
        buffer = self.console.get_buffer()
        buffer.set_text("")

    def show_error(self, message):
        dialog = Gtk.MessageDialog(
            transient_for=self.window,
            flags=0,
            message_type=Gtk.MessageType.ERROR,
            buttons=Gtk.ButtonsType.OK,
            text="Error",
        )
        dialog.format_secondary_text(message)
        dialog.run()
        dialog.destroy()

    def get_text_editor_content(self):
        buffer = self.text_editor.get_buffer()
        start_iter, end_iter = buffer.get_bounds()
        return buffer.get_text(start_iter, end_iter, True)

if __name__ == "__main__":
    app = PythonIDE()
    app.run(sys.argv)
