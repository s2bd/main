public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void square() {
        double tempReal = real * real - imaginary * imaginary;
        imaginary = 2 * real * imaginary;
        real = tempReal;
    }

    public double magnitudeSquared() {
        return real * real + imaginary * imaginary;
    }
}
