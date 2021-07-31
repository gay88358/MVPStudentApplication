package adapter.mainview;

import adapter.Navigator;
import adapter.createstudent.JFrameCreateStudentView;
import adapter.editstudent.JFrameEditStudentViewImp;
import application.StudentEventListener;
import application.StudentModel;
import application.port.in.getstudents.StudentDto;
import application.port.in.getstudents.GetAllStudentsUseCase;

import java.util.List;
import java.util.UUID;

public class StudentMainViewPresentationModel implements StudentEventListener {
    private final GetAllStudentsUseCase getAllStudentUseCase;
    private StudentModel studentModel;
    private Navigator navigator;
    private StudentMainView view;


    public StudentMainViewPresentationModel(GetAllStudentsUseCase getAllStudentsUseCase, StudentModel studentModel, Navigator navigator) {
        this.getAllStudentUseCase = getAllStudentsUseCase;
        this.studentModel = studentModel;
        this.studentModel.subscribe(this);
        this.navigator = navigator;
    }

    public void setView(StudentMainView view) {
        this.view = view;
    }

    public void initialize() {
        List<StudentDto> studentDtoList = this.getAllStudentUseCase.getAllStudents();
        this.view.setStudents(studentDtoList);
        this.view.setEditButtonEnable(false);
    }

    public void selectStudent(UUID studentId) {
        this.studentModel.selectStudent(studentId);
        this.view.setEditButtonEnable(this.studentModel.hasStudentSelected());
    }

    public void openEditStudentView() {
        this.navigator.navigateTo(JFrameEditStudentViewImp.class, this.studentModel.getSelectedStudent().get());
    }

    public void openCreateStudentView() {
        this.navigator.navigateTo(JFrameCreateStudentView.class, null);
    }

    @Override
    public void studentCreated(UUID studentId, String name, Integer age) {
        initialize();
    }

    @Override
    public void studentUpdated(UUID id, String name, Integer age) {
        initialize();
    }
}
