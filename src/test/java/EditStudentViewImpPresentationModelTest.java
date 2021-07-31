import adapter.editstudent.EditStudentView;
import adapter.editstudent.EditStudentViewPresentationModel;
import application.StudentModel;
import application.port.in.getstudents.StudentDto;
import application.port.in.editstudent.EditStudentInput;
import application.port.in.editstudent.EditStudentUseCase;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EditStudentViewImpPresentationModelTest {

    @Test
    void cancel_edit_student_information() {
        FakeEditStudentUseCase useCase = new FakeEditStudentUseCase();
        FakeEditStudentViewImp view = new FakeEditStudentViewImp();
        FakeStudentModel studentModel = new FakeStudentModel();
        EditStudentViewPresentationModel pm = createEditStudentViewPM(view, useCase, studentModel);

        pm.cancel();

        studentModel.shouldClearSelectedStudent();
    }


    @Test
    void save_student_information() {
        FakeEditStudentUseCase useCase = new FakeEditStudentUseCase();
        StudentDto studentDto = new StudentDto(UUID.randomUUID(), "Mars", 25);
        FakeEditStudentViewImp view = new FakeEditStudentViewImp();
        EditStudentViewPresentationModel pm = createEditStudentViewPM(view, useCase);

        pm.setStudentInformation(studentDto);

        pm.save();

        assertEquals(useCase.input, new EditStudentInput(studentDto.getId(), studentDto.getName(), studentDto.getAge()));
    }

    private static class FakeEditStudentUseCase implements EditStudentUseCase {

        private EditStudentInput input;
        @Override
        public void execute(EditStudentInput input) {
            this.input = input;
        }

    }
    @Test
    void edit_student_information() {
        StudentDto studentDto = new StudentDto(UUID.randomUUID(), "Mars", 25);
        FakeEditStudentViewImp view = new FakeEditStudentViewImp();
        EditStudentViewPresentationModel pm = createEditStudentViewPM(view);

        pm.setStudentInformation(studentDto);
        view.shouldEnableSaveButton();

        pm.setName("");
        pm.setAge(0);
        view.shouldDisableSaveButton();

        pm.setName("Amber");
        pm.setAge(25);
        view.shouldEnableSaveButton();
    }

    private static class FakeEditStudentViewImp implements EditStudentView {

        private boolean isEnabled = false;
        public void shouldDisableSaveButton() {
            assertFalse(isEnabled);
        }

        public void shouldEnableSaveButton() {
            assertTrue(isEnabled);
        }

        @Override
        public void setSaveButtonEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
        }

    }

    EditStudentViewPresentationModel createEditStudentViewPM(EditStudentView view) {
        return createEditStudentViewPM(view, new FakeEditStudentUseCase(), null);
    }

    EditStudentViewPresentationModel createEditStudentViewPM(EditStudentView view, EditStudentUseCase useCase) {
        return createEditStudentViewPM(view, useCase, null);
    }

    EditStudentViewPresentationModel createEditStudentViewPM(EditStudentView view, EditStudentUseCase useCase, StudentModel studentModel) {
        EditStudentViewPresentationModel pm = new EditStudentViewPresentationModel(useCase, studentModel);
        pm.setView(view);
        return pm;
    }
}