// ServiceApp.java
import java.awt.*;
import javax.swing.*;

public class ServiceApp extends JFrame {
    private CardLayout layout;
    private JPanel container;
    private LoginPage loginPage;
    private Dashboard dashboard;

    public ServiceApp() {
        setTitle("Service Request Management System");
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        container = new JPanel(layout);

        loginPage = new LoginPage(this);
        dashboard = new Dashboard(this);

        container.add(loginPage, "Login");
        container.add(dashboard, "Dashboard");

        add(container);
        showLogin();
    }

    public void showLogin() {
        layout.show(container, "Login");
    }

    public void showDashboard() {
        layout.show(container, "Dashboard");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServiceApp().setVisible(true);
        });
    }
}
