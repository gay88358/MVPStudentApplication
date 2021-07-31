package application.port.in.addstudent;

import java.util.UUID;

public interface AddStudentUseCase {
    UUID execute(AddStudentInput addStudentInput);
}
