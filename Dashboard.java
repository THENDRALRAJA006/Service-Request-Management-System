// Dashboard.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JPanel {
    private ServiceApp parent;
    private java.util.List<Request> requests = new ArrayList<>();
    private DefaultTableModel tableModel;

    private JTextField idField, requesterField, contactField, technicianField, remarksField;
    private JComboBox<String> categoryBox, priorityBox, statusBox;
    private JTable table;

    public Dashboard(ServiceApp parent) {
        this.parent = parent;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        initUI();
    }

    private void initUI() {
        JLabel header = new JLabel("Service Request Management", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(header, BorderLayout.NORTH);

        // Left form panel
        JPanel form = new JPanel(new GridLayout(9,2,6,6));
        form.setBorder(BorderFactory.createTitledBorder("Request Details"));

        idField = new JTextField();
        requesterField = new JTextField();
        contactField = new JTextField();
        categoryBox = new JComboBox<>(new String[]{"General","IT","Maintenance","HR","Other"});
        technicianField = new JTextField();
        priorityBox = new JComboBox<>(new String[]{"Low","Medium","High"});
        statusBox = new JComboBox<>(new String[]{"Pending","In Progress","Completed"});
        remarksField = new JTextField();

        form.add(new JLabel("Request ID:")); form.add(idField);
        form.add(new JLabel("Requester Name:")); form.add(requesterField);
        form.add(new JLabel("Contact No.:")); form.add(contactField);
        form.add(new JLabel("Category:")); form.add(categoryBox);
        form.add(new JLabel("Assigned Technician:")); form.add(technicianField);
        form.add(new JLabel("Priority:")); form.add(priorityBox);
        form.add(new JLabel("Status:")); form.add(statusBox);
        form.add(new JLabel("Remarks:")); form.add(remarksField);

        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View All");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");
        JButton logoutBtn = new JButton("Logout");

        addBtn.addActionListener(e -> addRequest());
        viewBtn.addActionListener(e -> refreshTable());
        updateBtn.addActionListener(e -> updateRequest());
        deleteBtn.addActionListener(e -> deleteRequest());
        clearBtn.addActionListener(e -> clearForm());
        logoutBtn.addActionListener(e -> parent.showLogin());

        JPanel btnPanel = new JPanel(new GridLayout(3,2,6,6));
        btnPanel.add(addBtn); btnPanel.add(viewBtn);
        btnPanel.add(updateBtn); btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn); btnPanel.add(logoutBtn);

        JPanel left = new JPanel(new BorderLayout(8,8));
        left.add(form, BorderLayout.CENTER);
        left.add(btnPanel, BorderLayout.SOUTH);

        // Right table panel
        String[] cols = {"ID","Requester","Contact","Category","Technician","Priority","Status","Remarks"};
        tableModel = new DefaultTableModel(cols,0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int r = table.getSelectedRow();
                if (r >= 0) loadRequestToForm(r);
            }
        });

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Requests"));

        add(left, BorderLayout.WEST);
        add(tableScroll, BorderLayout.CENTER);
    }

    private void addRequest() {
        try {
            String id = idField.getText().trim();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Request ID required"); return; }
            // Prevent duplicate ID
            for (Request req : requests) if (req.id.equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(this, "Duplicate Request ID!"); return;
            }
            Request r = new Request(
                    id,
                    requesterField.getText().trim(),
                    contactField.getText().trim(),
                    (String)categoryBox.getSelectedItem(),
                    technicianField.getText().trim(),
                    (String)priorityBox.getSelectedItem(),
                    (String)statusBox.getSelectedItem(),
                    remarksField.getText().trim()
            );
            requests.add(r);
            JOptionPane.showMessageDialog(this, "Request Added Successfully!");
            clearForm();
            refreshTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid details!");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Request r : requests) {
            tableModel.addRow(new Object[]{r.id, r.requester, r.contact, r.category, r.technician, r.priority, r.status, r.remarks});
        }
    }

    private void loadRequestToForm(int row) {
        idField.setText((String)tableModel.getValueAt(row,0));
        requesterField.setText((String)tableModel.getValueAt(row,1));
        contactField.setText((String)tableModel.getValueAt(row,2));
        categoryBox.setSelectedItem((String)tableModel.getValueAt(row,3));
        technicianField.setText((String)tableModel.getValueAt(row,4));
        priorityBox.setSelectedItem((String)tableModel.getValueAt(row,5));
        statusBox.setSelectedItem((String)tableModel.getValueAt(row,6));
        remarksField.setText((String)tableModel.getValueAt(row,7));
    }

    private void updateRequest() {
        String id = idField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter Request ID to update"); return; }
        boolean found = false;
        for (Request r : requests) {
            if (r.id.equalsIgnoreCase(id)) {
                r.requester = requesterField.getText().trim();
                r.contact = contactField.getText().trim();
                r.category = (String)categoryBox.getSelectedItem();
                r.technician = technicianField.getText().trim();
                r.priority = (String)priorityBox.getSelectedItem();
                r.status = (String)statusBox.getSelectedItem();
                r.remarks = remarksField.getText().trim();
                found = true;
                break;
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(this, "Request Updated!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Request ID not found!");
        }
    }

    private void deleteRequest() {
        String id = idField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter Request ID to delete"); return; }
        boolean removed = requests.removeIf(r -> r.id.equalsIgnoreCase(id));
        if (removed) {
            JOptionPane.showMessageDialog(this, "Request Deleted!");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Request ID not found!");
        }
    }

    private void clearForm() {
        idField.setText("");
        requesterField.setText("");
        contactField.setText("");
        technicianField.setText("");
        remarksField.setText("");
        categoryBox.setSelectedIndex(0);
        priorityBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
    }
}
