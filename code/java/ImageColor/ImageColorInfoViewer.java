import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageColorInfoViewer extends JFrame {

    private JLabel imageLabel;
    private JLabel colorInfoLabel;
    private JPanel colorSquarePanel;

    public ImageColorInfoViewer() {
        setTitle("Image Color Info Viewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Image label to display the image
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Color info label to display RGB and HEX color information
        colorInfoLabel = new JLabel();
        colorInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Color square panel to display the color
        colorSquarePanel = new JPanel();
        colorSquarePanel.setPreferredSize(new Dimension(20, 20));
        colorSquarePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Panel to hold the image label, color info label, and color square panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imageLabel, BorderLayout.CENTER);

        JPanel colorPanel = new JPanel(new BorderLayout());
        colorPanel.add(colorInfoLabel, BorderLayout.CENTER);
        colorPanel.add(colorSquarePanel, BorderLayout.EAST);

        panel.add(colorPanel, BorderLayout.SOUTH);

        // Add panel to the frame
        add(panel);

        // Add menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open Image");
        openMenuItem.addActionListener(e -> openImage());
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Add mouse motion listener to track mouse movement over the image
        imageLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateColorInfo(e.getPoint());
            }
        });
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".jpeg") || f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayImage(selectedFile);
        }
    }

    private void displayImage(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(img);
            imageLabel.setIcon(icon);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateColorInfo(Point point) {
        if (imageLabel.getIcon() == null) return;

        BufferedImage img = new BufferedImage(imageLabel.getWidth(), imageLabel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        imageLabel.paint(g);
        g.dispose();

        int pixelColor = img.getRGB(point.x, point.y);
        Color color = new Color(pixelColor);

        String rgbInfo = String.format("RGB: (%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
        String hexInfo = String.format("HEX: #%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

        colorInfoLabel.setText(rgbInfo + "   |   " + hexInfo);
        colorSquarePanel.setBackground(color);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageColorInfoViewer viewer = new ImageColorInfoViewer();
            viewer.setVisible(true);
        });
    }
}
