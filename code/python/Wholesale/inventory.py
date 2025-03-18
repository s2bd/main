import tkinter as tk
from tkinter import ttk

class InventoryPage:
    def __init__(self, parent):
        self.parent = parent
        
        # Label for the inventory section
        inventory_label = ttk.Label(self.parent, text="Manage Inventory", font=("Arial", 14))
        inventory_label.grid(row=0, column=0, columnspan=3, pady=10)

        # Table to display inventory items
        columns = ("Name", "Quantity", "Price", "Category")
        self.inventory_tree = ttk.Treeview(self.parent, columns=columns, show="headings")
        for col in columns:
            self.inventory_tree.heading(col, text=col)
        self.inventory_tree.grid(row=1, column=0, columnspan=3, padx=10, pady=5, sticky="nsew")

        # Scrollbar for the inventory table
        inventory_scroll = ttk.Scrollbar(self.parent, orient="vertical", command=self.inventory_tree.yview)
        inventory_scroll.grid(row=1, column=3, pady=5, sticky="ns")
        self.inventory_tree.configure(yscrollcommand=inventory_scroll.set)

        # Buttons for inventory management
        add_button = ttk.Button(self.parent, text="Add Product", command=self.add_product_popup)
        add_button.grid(row=2, column=0, padx=5, pady=5)

        remove_button = ttk.Button(self.parent, text="Remove Product", command=self.remove_product_popup)
        remove_button.grid(row=2, column=1, padx=5, pady=5)

        update_button = ttk.Button(self.parent, text="Update Product", command=self.update_product_popup)
        update_button.grid(row=2, column=2, padx=5, pady=5)

    # Add other inventory management methods here
