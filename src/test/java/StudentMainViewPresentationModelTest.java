import adapter.NavigableView;
import adapter.Navigator;
import adapter.createstudent.JFrameCreateStudentView;
import adapter.editstudent.JFrameEditStudentViewImp;
import adapter.mainview.StudentMainView;
import adapter.mainview.StudentMainViewPresentationModel;
import application.StudentModel;
import application.StudentService;
import application.port.in.getstudents.GetAllStudentsUseCase;
import application.port.in.getstudents.StudentDto;
import domain.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StudentMainViewPresentationModelTest {

    @Test
    void open_create_student_view() {
        FakeNavigator fakeNavigator = new FakeNavigator();
        StudentMainViewPresentationModel pm = createPresentationModel(fakeNavigator);

        pm.openCreateStudentView();

        fakeNavigator.shouldNavigateTo(JFrameCreateStudentView.class);
    }

    @Test
    void open_edit_student_view() {
        FakeStudentModel studentModel = new FakeStudentModel();
        FakeNavigator fakeNavigator = new FakeNavigator();
        StudentMainViewPresentationModel pm = createPresentationModel(fakeNavigator, studentModel);
        studentModel.setSelectedStudent(new Student(UUID.randomUUID(), "Mars", 25));

        pm.openEditStudentView();

        fakeNavigator.shouldNavigateTo(JFrameEditStudentViewImp.class);
    }

    @Test
    void select_student() {
        FakeStudentModel fakeStudentModel = new FakeStudentModel();
        FakeStudentMainView fakeView = new FakeStudentMainView();
        StudentMainViewPresentationModel pm =
                createPresentationModel(fakeView, fakeStudentModel);
        pm.initialize();
        fakeView.editButtonShouldDisabled();

        UUID studentId = UUID.randomUUID();
        pm.selectStudent(studentId);

        fakeView.editButtonShouldEnabled();
    }

    @Test
    void initialize_student() {
        FakeStudentMainView fakeView = new FakeStudentMainView();
        StudentMainViewPresentationModel pm = createPresentationModel(fakeView);

        pm.initialize();

        fakeView.editButtonShouldDisabled();
        assertEquals(fakeView.students.size(), 2);
        assertStudentDto(fakeView.students.get(0), "Mars", 25);
        assertStudentDto(fakeView.students.get(1), "Amber", 25);
    }

    StudentMainViewPresentationModel createPresentationModel(Navigator navigator) {
        StudentMainView studentMainView = new FakeStudentMainView();
        FakeGetAllStudentsUseCase useCase = new FakeGetAllStudentsUseCase();
        StudentService studentService = new StudentService(null);
        StudentMainViewPresentationModel pm = new StudentMainViewPresentationModel(useCase, studentService, navigator);
        pm.setView(studentMainView);

        pm.initialize();
        return pm;
    }

    StudentMainViewPresentationModel createPresentationModel(Navigator navigator, StudentModel studentModel) {
        StudentMainView studentMainView = new FakeStudentMainView();
        FakeGetAllStudentsUseCase useCase = new FakeGetAllStudentsUseCase();
        StudentMainViewPresentationModel pm = new StudentMainViewPresentationModel(useCase, studentModel, navigator);
        pm.setView(studentMainView);

        pm.initialize();
        return pm;
    }

    private static class FakeNavigator implements Navigator {
        private String viewName = "";
        @Override
        public <T extends NavigableView> void navigateTo(Class<T> viewClass, Object model) {
            this.viewName = viewClass.getSimpleName();
        }

        public <T extends NavigableView> void shouldNavigateTo(Class<T> zlass) {
            assertEquals(this.viewName, zlass.getSimpleName());
        }
    }



    private StudentMainViewPresentationModel createPresentationModel(StudentMainView studentMainView, StudentModel studentModel) {
        FakeGetAllStudentsUseCase useCase = new FakeGetAllStudentsUseCase();
        StudentMainViewPresentationModel pm = new StudentMainViewPresentationModel(useCase, studentModel, new FakeNavigator());
        pm.setView(studentMainView);
        return pm;
    }

// SRP violate edit change create, create change edit, main change edit and create
    private StudentMainViewPresentationModel createPresentationModel(StudentMainView studentMainView) {
        FakeGetAllStudentsUseCase useCase = new FakeGetAllStudentsUseCase();
        StudentService studentService = new StudentService(null);
        StudentMainViewPresentationModel pm = new StudentMainViewPresentationModel(useCase, studentService, new FakeNavigator());
        pm.setView(studentMainView);
        return pm;
    }


    private void assertStudentDto(StudentDto studentDto, String name, Integer age) {
        assertEquals(studentDto.getAge(), age);
        assertEquals(studentDto.getName(), name);
    }

    private static class FakeGetAllStudentsUseCase implements GetAllStudentsUseCase {
        @Override
        public List<StudentDto> getAllStudents() {
            List<StudentDto> result = new ArrayList<>();
            result.add(new StudentDto(UUID.randomUUID(), "Mars", 25));
            result.add(new StudentDto(UUID.randomUUID(), "Amber", 25));
            return result;
        }
    }

    private static class FakeStudentMainView implements StudentMainView {
        private List<StudentDto> students = new ArrayList<>();
        private boolean isEditButtonEnabled = false;

        @Override
        public void setStudents(List<StudentDto> studentDtoList) {
            this.students = studentDtoList;
        }

        @Override
        public void setEditButtonEnable(Boolean isEnabled) {
            this.isEditButtonEnabled = isEnabled;
        }

        public void editButtonShouldDisabled() {
            assertFalse(isEditButtonEnabled);
        }

        public void editButtonShouldEnabled() {
            assertTrue(isEditButtonEnabled);
        }
    }
}