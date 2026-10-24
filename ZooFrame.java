import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ZooFrame extends JFrame {
    private ArrayList<Animal> zooList;

    private JTextField txtName;
    private JTextField txtAge;
    private JComboBox<String> cbType;
    private JTextField txtFurColor;
    private JCheckBox chkCanFly;
    private JTextArea txtLog;
    private JButton btnAdd;

    private JPanel specificPanel;
    private CardLayout cardLayout;

    public ZooFrame() {
        zooList = new ArrayList<>();

        setTitle("Digital Zoo Manager");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Input Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Animal Form"));

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Type:"));
        String[] types = {"Generic Mammal", "Generic Bird"};
        cbType = new JComboBox<>(types);
        formPanel.add(cbType);

        // Panel khusus untuk atribut unik (Fur Color / Can Fly)
        cardLayout = new CardLayout();
        specificPanel = new JPanel(cardLayout);

        JPanel mammalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtFurColor = new JTextField(15);
        mammalPanel.add(new JLabel("Fur Color: "));
        mammalPanel.add(txtFurColor);

        JPanel birdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        chkCanFly = new JCheckBox("Can Fly?");
        birdPanel.add(chkCanFly);

        specificPanel.add(mammalPanel, "Mammal");
        specificPanel.add(birdPanel, "Bird");

        formPanel.add(new JLabel("Specific Attribute:"));
        formPanel.add(specificPanel);

        // Event listener saat tipe hewan diubah di JComboBox
        cbType.addActionListener(e -> {
            if (cbType.getSelectedItem().equals("Generic Mammal")) {
                cardLayout.show(specificPanel, "Mammal");
            } else {
                cardLayout.show(specificPanel, "Bird");
            }
        });

        // Tombol Add Animal
        btnAdd = new JButton("Add Animal");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);

        // Bagian Log / Display Area
        txtLog = new JTextArea(10, 40);
        txtLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Zoo Log"));

        // Event listener saat tombol "Add Animal" diklik
        btnAdd.addActionListener(e -> addAnimal());

        // Menyusun komponen ke dalam Frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addAnimal() {
        try {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age = Integer.parseInt(txtAge.getText().trim());
            String selectedType = (String) cbType.getSelectedItem();

            Animal newAnimal;

            if ("Generic Mammal".equals(selectedType)) {
                String furColor = txtFurColor.getText().trim();
                newAnimal = new Mammal(name, age, furColor);
                zooList.add(newAnimal);

                txtLog.append("Added a new Mammal! Info: " + newAnimal.getInfo() + ". Sound: " + newAnimal.makeSound() + "\n");
            } else {
                boolean canFly = chkCanFly.isSelected();
                newAnimal = new Bird(name, age, canFly);
                zooList.add(newAnimal);

                txtLog.append("Added a new Bird! Info: " + newAnimal.getInfo() + ". Sound: " + newAnimal.makeSound() + "\n");
            }

            // Reset field setelah input
            txtName.setText("");
            txtAge.setText("");
            txtFurColor.setText("");
            chkCanFly.setSelected(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age (integer).", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ZooFrame().setVisible(true);
        });
    }
}
