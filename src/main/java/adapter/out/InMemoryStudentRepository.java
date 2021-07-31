package adapter.out;

import application.port.in.getstudents.StudentDto;
import application.port.out.StudentRepository;
import domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryStudentRepository implements StudentRepository {
    private List<Student> studentList = new ArrayList<>();
    public InMemoryStudentRepository() {
        studentList.add(new Student(UUID.randomUUID(), "Mars", 25));
        studentList.add(new Student(UUID.randomUUID(), "Amber", 25));
        studentList.add(new Student(UUID.randomUUID(), "Toyz", 25));
    }

    @Override
    public Optional<Student> findStudentBy(UUID id) {
        return this.studentList
                .stream()
                .filter(s -> s.getId().toString().equals(id.toString()))
                .findFirst();
    }

    @Override
    public void create(Student student) {
        this.studentList.add(student);
    }

    @Override
    public List<StudentDto> findAllStudents() {
        return this.studentList
                .stream()
                .map(s -> new StudentDto(s.getId(), s.getName(), s.getAge()))
                .collect(Collectors.toList())
                ;
    }
}
