package adapter.in.editstudent;

import application.StudentModel;
import application.port.in.getstudents.StudentDto;
import application.port.in.editstudent.EditStudentInput;
import application.port.in.editstudent.EditStudentUseCase;

import java.util.UUID;

public class EditStudentViewPresentationModel {
    private EditStudentUseCase useCase;
    private StudentModel studentModel;

    private EditStudentView editStudentView;
    private String name = "";
    private Integer age = 0;
    private UUID id;

    public EditStudentViewPresentationModel(EditStudentUseCase useCase, StudentModel studentModel) {
        this.useCase = useCase;
        this.studentModel = studentModel;
    }


    public void setStudentInformation(StudentDto studentDto) {
        this.name = studentDto.getName();
        this.age = studentDto.getAge();
        this.id = studentDto.getId();

        this.editStudentView.setSaveButtonEnabled(canSave());
    }

    private boolean canSave() {
        return !this.name.isEmpty() && this.age > 0;
    }

    public void setView(EditStudentView view) {
        this.editStudentView = view;
    }

    public void setName(String name) {
        this.name = name;
        this.editStudentView.setSaveButtonEnabled(canSave());
    }

    public void setAge(Integer age) {
        this.age = age;
        this.editStudentView.setSaveButtonEnabled(canSave());
    }

    public void save() {
        if (!canSave()) {
            throw new RuntimeException("Invalid Data Can't Save");
        }

        this.useCase.execute(new EditStudentInput(id, name, age));
    }

    public UUID getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void cancel() {
        this.studentModel.clearSelectedStudent();
    }
}
