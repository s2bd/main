#include <QApplication>
#include <QMainWindow>
#include <QTableWidget>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QPushButton>
#include <QMenuBar>
#include <QFileDialog>
#include <QMessageBox>
#include <QTableWidgetItem>
#include <QTextStream>
#include <QInputDialog>
#include <QHeaderView>
#include <QFile>
#include <QDebug>
#include <QStack>
#include <QSpinBox>
#include <QLabel>

class InventoryManager : public QMainWindow {
public:
    InventoryManager(QWidget *parent = nullptr) : QMainWindow(parent) {
        setWindowTitle("Inventory Management Software");
        resize(1280, 720);

        QVBoxLayout *layout = new QVBoxLayout;

        // Search bar
        searchBar = new QLineEdit(this);
        searchBar->setPlaceholderText("Search...");
        layout->addWidget(searchBar);

        // Table for inventory
        tableWidget = new QTableWidget(0, 4, this);
        tableWidget->setHorizontalHeaderLabels(QStringList() << "Item Name" << "Quantity" << "Attributes" << "Actions");
        tableWidget->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
        layout->addWidget(tableWidget);

        // Add item area
        newItem = new QLineEdit(this);
        newItem->setPlaceholderText("Item Name");
        layout->addWidget(newItem);

        QLineEdit *attributeInput = new QLineEdit(this);
        attributeInput->setPlaceholderText("Attributes");
        layout->addWidget(attributeInput);

        QPushButton *addButton = new QPushButton("Add Item", this);
        layout->addWidget(addButton);

        // Menu bar
        QMenuBar *menuBar = this->menuBar();
        QMenu *fileMenu = menuBar->addMenu("File");
        QAction *saveAction = new QAction("Save", this);
        QAction *loadAction = new QAction("Load", this);
        QAction *exitAction = new QAction("Exit", this);
        fileMenu->addAction(saveAction);
        fileMenu->addAction(loadAction);
        fileMenu->addAction(exitAction);

        QMenu *editMenu = menuBar->addMenu("Edit");
        QAction *undoAction = new QAction("Undo", this);
        QAction *redoAction = new QAction("Redo", this);
        editMenu->addAction(undoAction);
        editMenu->addAction(redoAction);

        // Connections
        connect(addButton, &QPushButton::clicked, [this, attributeInput]() {
            addItem(newItem->text(), attributeInput->text());
            newItem->clear();
            attributeInput->clear();
        });

        connect(saveAction, &QAction::triggered, this, &InventoryManager::saveInventory);
        connect(loadAction, &QAction::triggered, this, &InventoryManager::loadInventory);
        connect(exitAction, &QAction::triggered, this, &InventoryManager::close);
        connect(searchBar, &QLineEdit::textChanged, this, &InventoryManager::filterItems);

        // Undo/Redo connections
        connect(undoAction, &QAction::triggered, this, &InventoryManager::undo);
        connect(redoAction, &QAction::triggered, this, &InventoryManager::redo);

        QWidget *centralWidget = new QWidget(this);
        centralWidget->setLayout(layout);
        setCentralWidget(centralWidget);
    }

private:
    QTableWidget *tableWidget;
    QLineEdit *searchBar;
    QLineEdit *newItem;

    // Undo/Redo stacks
    QStack<QString> undoStack;
    QStack<QString> redoStack;

    void addItem(const QString &itemName, const QString &attributes) {
        if (itemName.isEmpty()) return;

        // Handle existing item update logic...

        // Add to undo stack
        undoStack.push(itemName);
        redoStack.clear(); // Clear redo stack on new action
    }

    void undo() {
        if (!undoStack.isEmpty()) {
            QString lastAction = undoStack.pop();
            // Logic to undo the last action, e.g., removing an item
            redoStack.push(lastAction);
            // Update the GUI accordingly
        }
    }

    void redo() {
        if (!redoStack.isEmpty()) {
            QString actionToRedo = redoStack.pop();
            // Logic to redo the last undone action
            undoStack.push(actionToRedo);
            // Update the GUI accordingly
        }
    }

    void filterItems(const QString &searchTerm) {
        for (int row = 0; row < tableWidget->rowCount(); ++row) {
            QTableWidgetItem *item = tableWidget->item(row, 0);
            bool match = item->text().contains(searchTerm, Qt::CaseInsensitive);
            tableWidget->setRowHidden(row, !match);
        }
    }

    void saveInventory() {
        // Existing save logic...
    }

    void loadInventory() {
        // Existing load logic...
    }
};

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    InventoryManager manager;
    manager.show();
    return app.exec();
}
