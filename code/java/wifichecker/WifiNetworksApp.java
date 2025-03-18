import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class WifiNetworksApp {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                WifiNetworksApp window = new WifiNetworksApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public WifiNetworksApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Wi-Fi Networks");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(10, 10, 414, 241);
        frame.getContentPane().add(scrollPane);

        List<Network> wifiNetworks = getWifiNetworks();
        for (Network network : wifiNetworks) {
            appendToPane(textPane, "SSID: " + network.getSsid() + "\n", null);
            appendToPane(textPane, "    Password: " + network.getPassword() + "\n\n", null);
        }

        StyleContext context = new StyleContext();
        AttributeSet labelStyle = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Bold, true);
        AttributeSet passwordStyle = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
                java.awt.Color.GREEN);

        AttributeSet attrs = textPane.getStyledDocument().getStyle("label");
        textPane.getStyledDocument().setCharacterAttributes(0, textPane.getText().length(), attrs, false);
    }

    private void appendToPane(JTextPane tp, String msg, AttributeSet set) {
        try {
            tp.getStyledDocument().insertString(tp.getStyledDocument().getLength(), msg, set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private List<Network> getWifiNetworks() {
        List<Network> networks = new ArrayList<>();
        if (isWindows()) {
            networks.addAll(getWifiNetworksWindows());
        } else if (isLinux()) {
            networks.addAll(getWifiNetworksLinux());
        } else {
            networks.add(new Network("Unsupported OS", ""));
        }
        return networks;
    }

    private boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows");
    }

    private boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("linux");
    }

    private List<Network> getWifiNetworksWindows() {
        List<Network> networks = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show profiles");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("All User Profile")) {
                    String ssid = line.split(":")[1].trim();
                    try {
                        Process passwordProcess = Runtime.getRuntime()
                                .exec("netsh wlan show profile \"" + ssid + "\" key=clear");
                        BufferedReader passwordReader = new BufferedReader(
                                new InputStreamReader(passwordProcess.getInputStream()));
                        String passwordLine;
                        while ((passwordLine = passwordReader.readLine()) != null) {
                            if (passwordLine.contains("Key Content")) {
                                String password = passwordLine.split(":")[1].trim();
                                networks.add(new Network(ssid, password));
                                break;
                            }
                        }
                    } catch (IOException e) {
                        networks.add(new Network(ssid, "Could not retrieve password"));
                    }
                }
            }
        } catch (IOException e) {
            showErrorMessage("Error: " + e.getMessage());
        }
        return networks;
    }

    private List<Network> getWifiNetworksLinux() {
        List<Network> networks = new ArrayList<>();
        // Implement Linux-specific logic to retrieve Wi-Fi networks here
        return networks;
    }

    private void showErrorMessage(String message) {
        JFrame errorFrame = new JFrame();
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setBounds(100, 100, 300, 150);
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText(message);
        errorFrame.getContentPane().add(textPane);
        errorFrame.setVisible(true);
    }

    public class Network {
        private String ssid;
        private String password;

        public Network(String ssid, String password) {
            this.ssid = ssid;
            this.password = password;
        }

        public String getSsid() {
            return ssid;
        }

        public String getPassword() {
            return password;
        }
    }
}
