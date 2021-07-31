package application;

import application.port.addstudent.AddStudentInput;
import application.port.getstudents.StudentDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Repository Pattern
class StudentServiceTest {

    @Test
    public void add_student() {
        StudentService studentService = new StudentService();
        assertEquals(studentService.getAllStudents().size(), 3);

        AddStudentInput input = new AddStudentInput("Mars", 33);
        studentService.execute(input);

        assertEquals(studentService.getAllStudents().size(), 4);
        assertEquals(studentService.getAllStudents().get(3).getAge(), 33);
        assertEquals(studentService.getAllStudents().get(3).getName(), "Mars");
    }

    @Test
    public void update_student() {

    }

    @Test
    public void select_student() {

    }


}