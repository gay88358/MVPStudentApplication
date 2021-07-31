package application;

import domain.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentModel {
    void subscribe(StudentEventListener studentEventListener);
    Optional<Student> getSelectedStudent();
    void selectStudent(UUID uuid);
    boolean hasStudentSelected();
    void clearSelectedStudent();
}