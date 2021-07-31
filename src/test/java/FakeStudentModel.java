import application.StudentEventListener;
import application.StudentModel;
import domain.Student;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakeStudentModel implements StudentModel {
    private boolean hasStudentSelected = false;
    private Student selectedStudent;
    private boolean isClearSelectedStudent = false;

    public void setSelectedStudent(Student student) {
        this.selectedStudent = student;
    }

    @Override
    public void subscribe(StudentEventListener studentEventListener) {

    }

    @Override
    public boolean hasStudentSelected() {
        return hasStudentSelected;
    }

    @Override
    public void selectStudent(UUID uuid) {
        this.hasStudentSelected = true;
    }

    @Override
    public Optional<Student> getSelectedStudent() {
        return Optional.ofNullable(selectedStudent);
    }

    @Override
    public void clearSelectedStudent() {
        this.isClearSelectedStudent = true;
    }

    public void shouldClearSelectedStudent() {
        assertTrue(isClearSelectedStudent);
    }
}