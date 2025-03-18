#include <QApplication>
#include <QWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QVBoxLayout>
#include <cmath>

class Calculator : public QWidget {
public:
    Calculator(QWidget *parent = nullptr);

private slots:
    void calculate();

private:
    QLineEdit *input;
};

Calculator::Calculator(QWidget *parent) : QWidget(parent) {
    QVBoxLayout *layout = new QVBoxLayout(this);
    input = new QLineEdit(this);
    QPushButton *button = new QPushButton("Calculate", this);

    layout->addWidget(input);
    layout->addWidget(button);

    connect(button, &QPushButton::clicked, this, &Calculator::calculate);
}

void Calculator::calculate() {
    QString text = input->text();
    QStringList parts = text.split(' ');
    if (parts.size() != 2) return;

    double value = parts[0].toDouble();
    QString operation = parts[1];
    double result = 0.0;

    if (operation == "sqrt") result = sqrt(value);
    else if (operation == "log") result = log(value);
    else if (operation == "sin") result = sin(value);
    else if (operation == "cos") result = cos(value);
    else if (operation == "tan") result = tan(value);

    input->setText(QString("Result: %1").arg(result));
}

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    Calculator calculator;
    calculator.setWindowTitle("Scientific Calculator");
    calculator.resize(300, 200);
    calculator.show();
    return app.exec();
}
