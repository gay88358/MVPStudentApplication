package application;

import application.port.in.addstudent.AddStudentInput;
import application.port.in.addstudent.AddStudentUseCase;
import application.port.in.editstudent.EditStudentInput;
import application.port.in.editstudent.EditStudentUseCase;
import application.port.in.getstudents.GetAllStudentsUseCase;
import application.port.in.getstudents.StudentDto;
import application.port.out.StudentRepository;
import domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StudentService implements StudentModel, AddStudentUseCase, EditStudentUseCase, GetAllStudentsUseCase {
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

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void selectStudent(UUID id) {
        this.currentSelectedStudent = this.studentRepository.findStudentBy(id);
    }

    public void clearSelectedStudent() {
        this.currentSelectedStudent = Optional.empty();
    }

    @Override
    public UUID execute(AddStudentInput addStudentInput) {
        Student student = new Student(UUID.randomUUID(), addStudentInput.getName(), addStudentInput.getAge());
        this.studentEventListeners
                .forEach(l -> l.studentCreated(student.getId(), student.getName(), student.getAge()));
        this.studentRepository.create(student);
        return student.getId();
    }

    @Override
    public void execute(EditStudentInput input) {
        String id = input.getId().toString();
        Student student = studentRepository.findStudentBy(input.getId())
                .orElseThrow(() -> new RuntimeException("Invalid student id: " + id))
                ;

        student.setName(input.getName());
        student.setAge(input.getAge());

        this.studentEventListeners
                .forEach(l -> l.studentUpdated(student.getId(), student.getName(), student.getAge()));
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return this.studentRepository.findAllStudents();
    }
}
