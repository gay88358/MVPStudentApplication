package adapter.createstudent;

import application.port.addstudent.AddStudentInput;
import application.port.addstudent.AddStudentUseCase;

public class CreateStudentViewPresentationModel {
    private CreateStudentView createStudentView;
    private AddStudentUseCase addStudentUseCase;
    private String newName = "";
    private Integer newAge = 0;

    public CreateStudentViewPresentationModel(AddStudentUseCase addStudentUseCase) {
        this.addStudentUseCase = addStudentUseCase;
    }

    public void enterName(String newName) {
        this.newName = newName;
        this.createStudentView.setCreateButtonEnabled(isValidStudentInformation());
    }

    public void enterAge(Integer newAge) {
        this.newAge = newAge;
        this.createStudentView.setCreateButtonEnabled(isValidStudentInformation());
    }

    private boolean isValidStudentInformation() {
        return !this.newName.isEmpty() && this.newAge > 0;
    }

    public void createStudent() {
        AddStudentInput input = new AddStudentInput(this.newName, this.newAge);
        this.addStudentUseCase.execute(input);
    }

    public void setView(CreateStudentView createStudentView) {
        this.createStudentView = createStudentView;
    }

    public void initialize() {
        this.createStudentView.setCreateButtonEnabled(false);
    }
}
