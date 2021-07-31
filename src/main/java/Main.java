import adapter.in.NavigableView;
import adapter.in.Navigator;
import adapter.in.createstudent.CreateStudentViewPresentationModel;
import adapter.in.createstudent.JFrameCreateStudentView;
import adapter.in.editstudent.EditStudentViewPresentationModel;
import adapter.in.editstudent.JFrameEditStudentViewImp;
import adapter.in.mainview.*;
import adapter.out.InMemoryStudentRepository;
import application.StudentService;
import domain.Student;

import javax.swing.*;

public class Main implements Navigator {
    private StudentService studentService;
    private StudentMainViewPresentationModel pm;

    public Main(StudentService studentService) {
        this.studentService = studentService;
    }

    public static void main(String[] args) {
        StudentService studentService = new StudentService(new InMemoryStudentRepository());
        Main main = new Main(studentService);
        main.start();
    }

    private void start() {
        SwingUtilities.invokeLater(() -> new JFrameStudentMainView(
                createStudentListTableView(),
                getStudentMainViewPm()
                )
        );
    }


    private StudentListTableView createStudentListTableView() {
        // composition root
        return new StudentListTableView(
                getStudentMainViewPm()
                );
    }

    private StudentMainViewPresentationModel getStudentMainViewPm() {
        if (this.pm == null) {
            this.pm = new StudentMainViewPresentationModel(this.studentService, this.studentService, this);
        }

        return this.pm;
    }

    private JFrameCreateStudentView openCreateStudentView() {
        return new JFrameCreateStudentView(new CreateStudentViewPresentationModel(this.studentService));
    }

    private JFrameEditStudentViewImp openEditStudentView(Object model) {
        return new JFrameEditStudentViewImp(new EditStudentViewPresentationModel(this.studentService, this.studentService), (Student)model);
    }

    @Override
    public <T extends NavigableView> void navigateTo(Class<T> viewClass, Object model) {
        // runtimeException when match wrong name
        if (viewClass.getSimpleName().equals("JFrameCreateStudentView")) {
            openCreateStudentView();
        } else if (viewClass.getSimpleName().equals("JFrameEditStudentViewImp")) {
            openEditStudentView(model);
        } else {
            throw new RuntimeException("Invalid View Class: " + viewClass.getSimpleName());
        }

    }
}
