import tkinter as tk
from tkinter import ttk
from datetime import datetime

class WholesaleSurgicalApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Wholesale Surgical Product Manager")
        
        # Initialize empty lists for inventory, orders, and sales
        self.inventory = []
        self.orders = []
        self.sales = []

        # Create notebook widget to manage different sections
        self.notebook = ttk.Notebook(self.root)
        self.notebook.pack(fill=tk.BOTH, expand=True)

        # Inventory Management Section
        self.inventory_frame = ttk.Frame(self.notebook)
        self.notebook.add(self.inventory_frame, text="Inventory Management")
        self.create_inventory_widgets()

        # Order Management Section
        self.order_frame = ttk.Frame(self.notebook)
        self.notebook.add(self.order_frame, text="Order Management")
        self.create_order_widgets()

        # Sales Management Section
        self.sales_frame = ttk.Frame(self.notebook)
        self.notebook.add(self.sales_frame, text="Sales Management")
        self.create_sales_widgets()

        # Reporting Section
        self.reporting_frame = ttk.Frame(self.notebook)
        self.notebook.add(self.reporting_frame, text="Reporting")
        self.create_reporting_widgets()

    def create_inventory_widgets(self):
        # Label for the inventory section
        inventory_label = ttk.Label(self.inventory_frame, text="Manage Inventory", font=("Arial", 14))
        inventory_label.grid(row=0, column=0, columnspan=3, pady=10)

        # Table to display inventory items
        columns = ("Name", "Quantity", "Price", "Category")
        self.inventory_tree = ttk.Treeview(self.inventory_frame, columns=columns, show="headings")
        for col in columns:
            self.inventory_tree.heading(col, text=col)
        self.inventory_tree.grid(row=1, column=0, columnspan=3, padx=10, pady=5, sticky="nsew")

        # Scrollbar for the inventory table
        inventory_scroll = ttk.Scrollbar(self.inventory_frame, orient="vertical", command=self.inventory_tree.yview)
        inventory_scroll.grid(row=1, column=3, pady=5, sticky="ns")
        self.inventory_tree.configure(yscrollcommand=inventory_scroll.set)

        # Buttons for inventory management
        add_button = ttk.Button(self.inventory_frame, text="Add Product", command=self.add_product_popup)
        add_button.grid(row=2, column=0, padx=5, pady=5)

        remove_button = ttk.Button(self.inventory_frame, text="Remove Product", command=self.remove_product_popup)
        remove_button.grid(row=2, column=1, padx=5, pady=5)

        update_button = ttk.Button(self.inventory_frame, text="Update Product", command=self.update_product_popup)
        update_button.grid(row=2, column=2, padx=5, pady=5)

    def add_product_popup(self):
        # Function to open a popup for adding a product
        popup = tk.Toplevel()
        popup.title("Add Product")

        # Entry fields for product details
        name_label = ttk.Label(popup, text="Name:")
        name_label.grid(row=0, column=0, padx=5, pady=5, sticky="e")
        self.name_entry = ttk.Entry(popup)
        self.name_entry.grid(row=0, column=1, padx=5, pady=5)

        quantity_label = ttk.Label(popup, text="Quantity:")
        quantity_label.grid(row=1, column=0, padx=5, pady=5, sticky="e")
        self.quantity_entry = ttk.Entry(popup)
        self.quantity_entry.grid(row=1, column=1, padx=5, pady=5)

        price_label = ttk.Label(popup, text="Price:")
        price_label.grid(row=2, column=0, padx=5, pady=5, sticky="e")
        self.price_entry = ttk.Entry(popup)
        self.price_entry.grid(row=2, column=1, padx=5, pady=5)

        category_label = ttk.Label(popup, text="Category:")
        category_label.grid(row=3, column=0, padx=5, pady=5, sticky="e")
        self.category_entry = ttk.Entry(popup)
        self.category_entry.grid(row=3, column=1, padx=5, pady=5)

        # Button to add product
        add_button = ttk.Button(popup, text="Add", command=self.add_product_action)
        add_button.grid(row=4, column=0, columnspan=2, padx=5, pady=10)

    def add_product_action(self):
        # Function to add product based on data entered in the popup
        name = self.name_entry.get()
        quantity = int(self.quantity_entry.get())
        price = float(self.price_entry.get())
        category = self.category_entry.get()

        self.add_product(name, quantity, price, category)

        self.name_entry.delete(0, tk.END)
        self.quantity_entry.delete(0, tk.END)
        self.price_entry.delete(0, tk.END)
        self.category_entry.delete(0, tk.END)

    def remove_product_popup(self):
        # Function to open a popup for removing a product
        popup = tk.Toplevel()
        popup.title("Remove Product")

        # Combobox to select product
        product_label = ttk.Label(popup, text="Select Product:")
        product_label.grid(row=0, column=0, padx=5, pady=5, sticky="e")
        self.product_combobox = ttk.Combobox(popup, values=[item["Name"] for item in self.inventory])
        self.product_combobox.grid(row=0, column=1, padx=5, pady=5)

        # Button to remove product
        remove_button = ttk.Button(popup, text="Remove", command=self.remove_product_action)
        remove_button.grid(row=1, column=0, columnspan=2, padx=5, pady=10)

    def remove_product_action(self):
        # Function to remove selected product from the inventory
        item = self.product_combobox.get()
        self.remove_product(item)

        self.product_combobox.set("")

    def update_product_popup(self):
         # Function to open a popup for updating a product
        popup = tk.Toplevel()
        popup.title("Update Product")

        # Combobox to select product
        product_label = ttk.Label(popup, text="Select Product:")
        product_label.grid(row=0, column=0, padx=5, pady=5, sticky="e")
        self.product_combobox_update = ttk.Combobox(popup, values=[item["Name"] for item in self.inventory])
        self.product_combobox_update.grid(row=0, column=1, padx=5, pady=5)

        # Entry fields for updated product details
        quantity_label = ttk.Label(popup, text="New Quantity:")
        quantity_label.grid(row=1, column=0, padx=5, pady=5, sticky="e")
        self.quantity_entry_update = ttk.Entry(popup)
        self.quantity_entry_update.grid(row=1, column=1, padx=5, pady=5)

        price_label = ttk.Label(popup, text="New Price:")
        price_label.grid(row=2, column=0, padx=5, pady=5, sticky="e")
        self.price_entry_update = ttk.Entry(popup)
        self.price_entry_update.grid(row=2, column=1, padx=5, pady=5)

        category_label = ttk.Label(popup, text="New Category:")
        category_label.grid(row=3, column=0, padx=5, pady=5, sticky="e")
        self.category_entry_update = ttk.Entry(popup)
        self.category_entry_update.grid(row=3, column=1, padx=5, pady=5)

        # Button to update product
        update_button = ttk.Button(popup, text="Update", command=self.update_product_action)
        update_button.grid(row=4, column=0, columnspan=2, padx=5, pady=10)

    def update_product_action(self):
        # Function to update product based on data entered in the popup
        product_name = self.product_combobox_update.get()
        new_quantity = int(self.quantity_entry_update.get())
        new_price = float(self.price_entry_update.get())
        new_category = self.category_entry_update.get()

        for product in self.inventory:
            if product["Name"] == product_name:
                product["Quantity"] = new_quantity
                product["Price"] = new_price
                product["Category"] = new_category
                break

        self.update_inventory_table()

        self.product_combobox_update.set("")
        self.quantity_entry_update.delete(0, tk.END)
        self.price_entry_update.delete(0, tk.END)
        self.category_entry_update.delete(0, tk.END)

    def create_inventory_widgets(self):
        # Label for the inventory section
        inventory_label = ttk.Label(self.inventory_frame, text="Manage Inventory", font=("Arial", 14))
        inventory_label.grid(row=0, column=0, columnspan=2, pady=10)

        # Table to display inventory items
        columns = ("Name", "Quantity", "Price", "Category")
        self.inventory_tree = ttk.Treeview(self.inventory_frame, columns=columns, show="headings")
        for col in columns:
            self.inventory_tree.heading(col, text=col)
        self.inventory_tree.grid(row=1, column=0, columnspan=2, padx=10, pady=5, sticky="nsew")

        # Scrollbar for the inventory table
        inventory_scroll = ttk.Scrollbar(self.inventory_frame, orient="vertical", command=self.inventory_tree.yview)
        inventory_scroll.grid(row=1, column=2, pady=5, sticky="ns")
        self.inventory_tree.configure(yscrollcommand=inventory_scroll.set)

    def create_order_widgets(self):
        # Label for the order section
        order_label = ttk.Label(self.order_frame, text="Manage Orders", font=("Arial", 14))
        order_label.grid(row=0, column=0, columnspan=2, pady=10)

        # Table to display orders
        order_columns = ("Order ID", "Product", "Quantity")
        self.order_tree = ttk.Treeview(self.order_frame, columns=order_columns, show="headings")
        for col in order_columns:
            self.order_tree.heading(col, text=col)
        self.order_tree.grid(row=1, column=0, columnspan=2, padx=10, pady=5, sticky="nsew")

        # Scrollbar for the order table
        order_scroll = ttk.Scrollbar(self.order_frame, orient="vertical", command=self.order_tree.yview)
        order_scroll.grid(row=1, column=2, pady=5, sticky="ns")
        self.order_tree.configure(yscrollcommand=order_scroll.set)

        # Button to place an order
        place_order_button = ttk.Button(self.order_frame, text="Place Order", command=self.place_order)
        place_order_button.grid(row=2, column=0, columnspan=2, padx=5, pady=5)

    def create_sales_widgets(self):
        # Label for the sales section
        sales_label = ttk.Label(self.sales_frame, text="Manage Sales", font=("Arial", 14))
        sales_label.grid(row=0, column=0, columnspan=2, pady=10)

        # Table to display sales
        sales_columns = ("Sale ID", "Product", "Quantity", "Price", "Total", "Date")
        self.sales_tree = ttk.Treeview(self.sales_frame, columns=sales_columns, show="headings")
        for col in sales_columns:
            self.sales_tree.heading(col, text=col)
        self.sales_tree.grid(row=1, column=0, columnspan=2, padx=10, pady=5, sticky="nsew")

        # Scrollbar for the sales table
        sales_scroll = ttk.Scrollbar(self.sales_frame, orient="vertical", command=self.sales_tree.yview)
        sales_scroll.grid(row=1, column=2, pady=5, sticky="ns")
        self.sales_tree.configure(yscrollcommand=sales_scroll.set)

    def create_reporting_widgets(self):
        # Button to generate report
        generate_report_button = ttk.Button(self.reporting_frame, text="Generate Report", command=self.generate_report)
        generate_report_button.pack(pady=20)

    def add_product(self, name, quantity, price, category):
        # Function to add a product to the inventory
        self.inventory.append({"Name": name, "Quantity": quantity, "Price": price, "Category": category})
        self.update_inventory_table()

    def remove_product(self, item):
        # Function to remove a product from the inventory
        for product in self.inventory:
            if product["Name"] == item:
                self.inventory.remove(product)
                break
        self.update_inventory_table()

    def place_order(self, product, quantity):
        # Function to place an order
        if not product:
            messagebox.showerror("Error", "Please select a product.")
            return

        if not quantity:
            messagebox.showerror("Error", "Please enter a quantity.")
            return

        product_name = product["Name"]
        if quantity > product["Quantity"]:
            messagebox.showerror("Error", f"Not enough {product_name} in stock.")
            return

        order_id = len(self.orders) + 1
        self.orders.append({"Order ID": order_id, "Product": product_name, "Quantity": quantity})
        self.update_order_table()

        # Deduct ordered quantity from inventory
        for item in self.inventory:
            if item["Name"] == product_name:
                item["Quantity"] -= quantity
                break

        self.update_inventory_table()

    def generate_report(self):
        # Function to generate a report
        # Here, you can implement logic to generate a report based on
        # inventory, orders, and sales data
        pass

    def update_inventory_table(self):
        # Function to update the inventory table with the current inventory data
        self.clear_treeview(self.inventory_tree)
        for item in self.inventory:
            self.inventory_tree.insert("", "end", values=(item["Name"], item["Quantity"], item["Price"], item["Category"]))

    def update_order_table(self):
        # Function to update the order table with the current order data
        self.clear_treeview(self.order_tree)
        for order in self.orders:
            self.order_tree.insert("", "end", values=(order["Order ID"], order["Product"], order["Quantity"]))

    def clear_treeview(self, tree):
        # Function to clear the contents of a Treeview widget
        for item in tree.get_children():
            tree.delete(item)

def main():
    root = tk.Tk()
    app = WholesaleSurgicalApp(root)
    root.mainloop()

if __name__ == "__main__":
    main()
