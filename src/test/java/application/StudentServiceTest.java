package application;

import application.port.in.addstudent.AddStudentInput;
import application.port.in.editstudent.EditStudentInput;
import application.port.in.getstudents.StudentDto;
import application.port.out.StudentRepository;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private FakeStudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        studentRepository = new FakeStudentRepository();
    }

    @Test
    public void get_all_students() {
        UUID marsId = createStudent("Mars", 25);
        UUID amberId = createStudent("Amber", 25);

        StudentService studentService = new StudentService(studentRepository);

        List<StudentDto> result = studentService.getAllStudents();
        assertEquals(result.size(), 2);
        assertEquals(result, Arrays.asList(
                new StudentDto(marsId, "Mars", 25),
                new StudentDto(amberId, "Amber", 25)
        ));
    }

    @Test
    public void add_student() {
        StudentService studentService = new StudentService(studentRepository);

        AddStudentInput input = new AddStudentInput("Mars", 33);
        UUID id = studentService.execute(input);

        Optional<Student> result = studentRepository.findStudentBy(id);
        assertTrue(result.isPresent());

        Student student = result.get();
        assertEquals("Mars", student.getName());
        assertEquals(33, student.getAge());
    }

    @Test
    public void update_student() {
        UUID id = createStudent("Mars", 33);

        StudentService studentService = new StudentService(studentRepository);
        EditStudentInput input = new EditStudentInput(id, "Amber", 30);
        studentService.execute(input);

        Optional<Student> result = studentRepository.findStudentBy(id);
        assertTrue(result.isPresent());

        Student student = result.get();
        assertEquals("Amber", student.getName());
        assertEquals(30, student.getAge());
    }

    private UUID createStudent(String name, Integer age) {
        Student student = new Student(UUID.randomUUID(), name, age);
        studentRepository.create(student);
        return student.getId();
    }

    @Test
    public void select_student() {
        UUID id = createStudent("mars", 25);
        StudentService studentService = new StudentService(studentRepository);

        studentService.selectStudent(id);
        assertTrue(studentService.hasStudentSelected());
        assertEquals(studentService.getSelectedStudent(), studentRepository.findStudentBy(id));

        studentService.clearSelectedStudent();
        assertFalse(studentService.hasStudentSelected());
        assertEquals(studentService.getSelectedStudent(), Optional.empty());
    }


    private static class FakeStudentRepository implements StudentRepository {
        private Map<String, Student> studentMap = new HashMap<>();

        @Override
        public Optional<Student> findStudentBy(UUID id) {
            return Optional.ofNullable(studentMap.get(id.toString()));
        }

        @Override
        public void create(Student student) {
            studentMap.put(student.getId().toString(), student);
        }

        @Override
        public List<StudentDto> findAllStudents() {
            return studentMap
                    .values()
                    .stream()
                    .map(s -> new StudentDto(s.getId(), s.getName(), s.getAge()))
                    .collect(Collectors.toList());
        }
    }
}