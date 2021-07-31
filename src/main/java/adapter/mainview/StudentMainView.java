package adapter.mainview;

import application.port.getstudents.StudentDto;

import java.util.List;

public interface StudentMainView {
    void setStudents(List<StudentDto> studentDtoList);
    void setEditButtonEnable(Boolean isEnabled);
}
