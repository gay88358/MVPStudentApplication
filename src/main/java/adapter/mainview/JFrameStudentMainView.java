package adapter.mainview;

import application.port.in.getstudents.StudentDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class JFrameStudentMainView extends JFrame implements StudentMainView
{
    private StudentMainViewPresentationModel pm;

    private StudentListTableView studentListTableView;
    private JButton editStudentButton;

    public JFrameStudentMainView(
            StudentListTableView studentListTableView,
            StudentMainViewPresentationModel pm) {
        StudentMainView v = setupViewComponent(studentListTableView);
        setStudentMainViewPM(pm, v);
    }

    private StudentMainView setupViewComponent(StudentListTableView studentListTableView) {
        this.setTitle("Table Example");
        this.studentListTableView = studentListTableView;
        JButton createStudentButton = createButton("Create Student", e -> pm.openCreateStudentView());
        this.editStudentButton = createButton("Edit Student", e -> pm.openEditStudentView());
        this.add(new JScrollPane(this.studentListTableView));
        addButtonsPanel(createStudentButton, this.editStudentButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        return this;
    }


    private void setStudentMainViewPM(StudentMainViewPresentationModel pm, StudentMainView view) {
        this.pm = pm;
        this.pm.setView(view);
        this.pm.initialize();
    }

    private JButton createButton(String text, ActionListener l) {
        JButton result = new JButton(text);
        result.addActionListener(l);
        return result;
    }

    private void addButtonsPanel(JButton createStudentButton, JButton editStudentButton) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(createStudentButton);
        bottomPanel.add(editStudentButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setStudents(List<StudentDto> studentDtoList) {
        this.studentListTableView.setStudents(studentDtoList);
    }

    @Override
    public void setEditButtonEnable(Boolean isEnabled) {
        this.editStudentButton.setEnabled(isEnabled);
    }
}
