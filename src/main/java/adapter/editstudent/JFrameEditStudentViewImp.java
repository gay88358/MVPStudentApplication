package adapter.editstudent;

import adapter.NavigableView;
import application.port.getstudents.StudentDto;
import domain.Student;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class JFrameEditStudentViewImp extends JFrame implements NavigableView, EditStudentView {
    private Container container = getContentPane();
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel ageLabel;
    private JTextField ageTextField;
    private JButton saveButton;
    private JButton cancelButton;

    private EditStudentViewPresentationModel pm;

    public JFrameEditStudentViewImp(EditStudentViewPresentationModel pm, Student student) {
        EditStudentView view = setupViewComponent(student);
        setEditStudentViewPM(pm, view, new StudentDto(student.getId(), student.getName(), student.getAge()));
    }

    private EditStudentView setupViewComponent(Student student) {
        // widgets
        this.nameLabel = new JLabel("Name");
        this.nameTextField = new JTextField();
        nameTextField.setText(student.getName());
        addNameTextFieldListener(this.nameTextField);

        this.ageLabel = new JLabel("Age");
        this.ageTextField = new JTextField();
        ageTextField.setText(student.getAge().toString());
        addAgeTextFieldListener(this.ageTextField);

        this.saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            pm.save();
            dispose();
        });

        this.cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            pm.cancel();
            dispose();
        });

        // layout
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        // metadata
        setTitle("Edit domain.Student");
        setVisible(true);
        setBounds(10, 10, 370, 600);
        setResizable(false);
        return this;
    }

    private void setEditStudentViewPM(EditStudentViewPresentationModel pm, EditStudentView view, StudentDto studentDto) {
        System.out.println("Initialize PM...");
        this.pm = pm;
        this.pm.setView(view);
        this.pm.setStudentInformation(studentDto);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        nameLabel.setBounds(50, 150, 100, 30);
        ageLabel.setBounds(50, 220, 100, 30);
        nameTextField.setBounds(150, 150, 150, 30);
        ageTextField.setBounds(150, 220, 150, 30);
        saveButton.setBounds(50, 300, 100, 30);
        cancelButton.setBounds(200, 300, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(ageLabel);
        container.add(nameTextField);
        container.add(ageTextField);
        container.add(saveButton);
        container.add(cancelButton);
    }

    private void addAgeTextFieldListener(JTextField ageTextField) {
        ageTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                pm.setAge(getAge());
            }

            public void removeUpdate(DocumentEvent e) {
                pm.setAge(getAge());
            }
            public void insertUpdate(DocumentEvent e) {
                pm.setAge(getAge());
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
                pm.setName(getStudentName());
            }
            public void removeUpdate(DocumentEvent e) {
                pm.setName(getStudentName());
            }
            public void insertUpdate(DocumentEvent e) {
                pm.setName(getStudentName());
            }
        });
    }

    String getStudentName() {
        return this.nameTextField.getText();
    }

    @Override
    public void setSaveButtonEnabled(boolean isEnabled) {
        this.saveButton.setEnabled(isEnabled);
    }
}
