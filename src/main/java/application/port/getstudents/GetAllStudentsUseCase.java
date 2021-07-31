package application.port.getstudents;

import java.util.List;

public interface GetAllStudentsUseCase {
    List<StudentDto> getAllStudents();
}
