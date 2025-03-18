import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class InventoryForm extends JFrame {
    private JTextField nameField, categoryField, quantityField, priceField;
    private JButton addButton, saveButton, generateReportButton;
    private JTextArea inventoryArea;
    private ArrayList<InventoryItem> inventory;

    public InventoryForm() {
        inventory = new ArrayList<>();
        initComponents();
    }

    // Initialize the GUI components
    private void initComponents() {
        setTitle("Inventory Management");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameField = new JTextField(20);
        categoryField = new JTextField(20);
        quantityField = new JTextField(5);
        priceField = new JTextField(10);

        addButton = new JButton("Add Item");
        saveButton = new JButton("Save Inventory");
        generateReportButton = new JButton("Generate Report");

        inventoryArea = new JTextArea(10, 30);
        inventoryArea.setEditable(false);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);
        inputPanel.add(saveButton);
        inputPanel.add(generateReportButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(inventoryArea), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveInventoryToFile();
            }
        });

        generateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        loadInventoryFromFile();  // Load inventory data on startup
    }

    // Add an item to the inventory
    private void addItem() {
        String name = nameField.getText();
        String category = categoryField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        InventoryItem item = new InventoryItem(name, category, quantity, price);
        inventory.add(item);

        inventoryArea.append(item.toString() + "\n");

        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    // Save the inventory to a file
    private void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.txt"))) {
            for (InventoryItem item : inventory) {
                writer.write(item.toCSV() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Inventory saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the inventory from a file
    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                String category = fields[1];
                int quantity = Integer.parseInt(fields[2]);
                double price = Double.parseDouble(fields[3]);

                InventoryItem item = new InventoryItem(name, category, quantity, price);
                inventory.add(item);
                inventoryArea.append(item.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generate a report and save it to a file
    private void generateReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory_report.txt"))) {
            writer.write("Inventory Report\n");
            writer.write("=================\n");
            for (InventoryItem item : inventory) {
                writer.write(item.toString() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Report generated!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
