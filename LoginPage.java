// LoginPage.java
import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {
    private ServiceApp parent;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage(ServiceApp parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setBackground(new Color(235, 245, 255));

        JLabel title = new JLabel("SERVICE REQUEST LOGIN");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        usernameField = new JTextField(14);
        passwordField = new JPasswordField(14);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> authenticate());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++; add(userLabel, gbc);
        gbc.gridx = 1; add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++; add(passLabel, gbc);
        gbc.gridx = 1; add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        add(loginBtn, gbc);
    }

    private void authenticate() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword()).trim();
        // Default credential (change as needed)
        if (u.equals("admin") && p.equals("123")) {
            parent.showDashboard();
            // clear fields
            usernameField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
