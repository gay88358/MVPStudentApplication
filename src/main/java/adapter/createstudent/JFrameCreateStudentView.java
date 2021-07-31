package adapter.createstudent;

import adapter.NavigableView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;


public class JFrameCreateStudentView extends JFrame implements NavigableView, CreateStudentView {
    private Container container = getContentPane();
    private JLabel nameLabel;
    private JTextField nameTextField;

    private JLabel ageLabel;
    private JTextField ageTextField;

    private JButton createButton;
    private JButton cancelButton;

    private CreateStudentViewPresentationModel pm;

    public JFrameCreateStudentView(CreateStudentViewPresentationModel pm) {
        CreateStudentView view = setupViewComponent();
        setCreateStudentViewPM(pm, view);
    }

    private void setCreateStudentViewPM(CreateStudentViewPresentationModel pm, CreateStudentView view) {
        this.pm = pm;
        this.pm.setView(view);
        this.pm.initialize();
    }

    private JButton createButton(String text, ActionListener l) {
        JButton result = new JButton(text);
        result.addActionListener(l);
        return result;
    }

    private CreateStudentView setupViewComponent() {
        this.nameLabel = new JLabel("Name");
        this.nameTextField = new JTextField();
        addNameTextFieldListener(nameTextField);

        this.ageLabel = new JLabel("Age");
        this.ageTextField = new JTextField();
        addAgeTextFieldListener(ageTextField);


        cancelButton = createButton("Cancel", e -> dispose());
        createButton = createButton("Create", e -> {
            this.pm.createStudent();
            dispose();
        });

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();

        setTitle("Create domain.Student");
        setVisible(true);
        setBounds(10, 10, 370, 600);
        setResizable(false);
        return this;
    }

    private void addAgeTextFieldListener(JTextField ageTextField) {
        ageTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                pm.enterAge(getAge());
            }

            public void removeUpdate(DocumentEvent e) {
                pm.enterAge(getAge());
            }
            public void insertUpdate(DocumentEvent e) {
                pm.enterAge(getAge());
            }
        });
    }

    private Integer getAge() {
        if (ageTextField.getText().equals("")) {
            return 0;
        }
        // if age is string
        return Integer.parseInt(ageTextField.getText());
    }

    private void addNameTextFieldListener(JTextField nameTextField) {
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    pm.enterName(JFrameCreateStudentView.this.nameTextField.getText());
                });
            }
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    pm.enterName(JFrameCreateStudentView.this.nameTextField.getText());

                });

            }
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    pm.enterName(JFrameCreateStudentView.this.nameTextField.getText());
                });
            }
        });
    }



    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        nameLabel.setBounds(50, 150, 100, 30);
        ageLabel.setBounds(50, 220, 100, 30);
        nameTextField.setBounds(150, 150, 150, 30);
        ageTextField.setBounds(150, 220, 150, 30);
        createButton.setBounds(50, 300, 100, 30);
        cancelButton.setBounds(200, 300, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(ageLabel);
        container.add(nameTextField);
        container.add(ageTextField);
        container.add(createButton);
        container.add(cancelButton);
    }

    @Override
    public void setCreateButtonEnabled(boolean isEnabled) {
        this.createButton.setEnabled(isEnabled);
    }
}
