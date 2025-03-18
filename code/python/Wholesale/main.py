import tkinter as tk
from tkinter import ttk
from inventory import InventoryPage

class WholesaleSurgicalApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Wholesale Surgical Product Manager")
        
        # Create notebook widget to manage different sections
        self.notebook = ttk.Notebook(self.root)
        self.notebook.pack(fill=tk.BOTH, expand=True)

        # Inventory Management Section
        self.inventory_frame = ttk.Frame(self.notebook)
        self.notebook.add(self.inventory_frame, text="Inventory Management")
        InventoryPage(self.inventory_frame)

def main():
    root = tk.Tk()
    app = WholesaleSurgicalApp(root)
    root.mainloop()

if __name__ == "__main__":
    main()
