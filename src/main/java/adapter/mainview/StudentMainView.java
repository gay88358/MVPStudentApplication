package adapter.mainview;

import application.port.in.getstudents.StudentDto;

import java.util.List;

public interface StudentMainView {
    void setStudents(List<StudentDto> studentDtoList);
    void setEditButtonEnable(Boolean isEnabled);
}
