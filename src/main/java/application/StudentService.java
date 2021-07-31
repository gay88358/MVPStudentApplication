package application;

import application.port.addstudent.AddStudentInput;
import application.port.addstudent.AddStudentUseCase;
import application.port.editstudent.EditStudentInput;
import application.port.editstudent.EditStudentUseCase;
import application.port.getstudents.GetAllStudentsUseCase;
import application.port.getstudents.StudentDto;
import domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StudentService implements StudentModel, AddStudentUseCase, EditStudentUseCase, GetAllStudentsUseCase {
    private List<Student> studentList = new ArrayList<>();
    private Optional<Student> currentSelectedStudent = Optional.empty();



    private List<StudentEventListener> studentEventListeners = new ArrayList<>();

    public Optional<Student> getSelectedStudent() {
        return currentSelectedStudent;
    }

    @Override
    public void subscribe(StudentEventListener studentEventListener) {
        this.studentEventListeners.add(studentEventListener);
    }

    @Override
    public boolean hasStudentSelected() {
        return currentSelectedStudent.isPresent();
    }

    public StudentService() {
        studentList.add(new Student(UUID.randomUUID(), "Mars", 15));
        studentList.add(new Student(UUID.randomUUID(), "Amber", 15));
        studentList.add(new Student(UUID.randomUUID(), "Toyz", 15));
    }


    @Override
    public void selectStudent(UUID uuid) {
        this.currentSelectedStudent = this.studentList
                .stream()
                .filter(s -> s.getId().toString().equals(uuid.toString()))
                .findFirst();
    }

    public void clearSelectedStudent() {
        this.currentSelectedStudent = Optional.empty();
    }

    private Optional<Student> findStudent(UUID uuid) {
        return this.studentList
                .stream()
                .filter(s -> s.getId().toString().equals(uuid.toString()))
                .findFirst()
                ;
    }

    @Override
    public void execute(AddStudentInput addStudentInput) {
        Student student = new Student(UUID.randomUUID(), addStudentInput.getName(), addStudentInput.getAge());
        this.studentList.add(student);
        this.studentEventListeners
                .forEach(l -> l.studentCreated(student.getId(), student.getName(), student.getAge()));
    }

    @Override
    public void execute(EditStudentInput input) {
        String id = input.getId().toString();
        Student student = findStudent(input.getId())
                .orElseThrow(() -> new RuntimeException("Invalid student id: " + id))
                ;

        student.setName(input.getName());
        student.setAge(input.getAge());

        this.studentEventListeners
                .forEach(l -> l.studentUpdated(student.getId(), student.getName(), student.getAge()));
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return this.studentList
                .stream()
                .map(s -> new StudentDto(s.getId(), s.getName(), s.getAge()))
                .collect(Collectors.toList())
        ;
    }
}
