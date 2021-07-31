package application.port.out;

import application.port.in.getstudents.StudentDto;
import domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    Optional<Student> findStudentBy(UUID id);
    void create(Student student);
    List<StudentDto> findAllStudents();
}
