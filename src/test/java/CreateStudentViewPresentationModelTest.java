import adapter.createstudent.CreateStudentView;
import adapter.createstudent.CreateStudentViewPresentationModel;
import application.port.in.addstudent.AddStudentInput;
import application.port.in.addstudent.AddStudentUseCase;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateStudentViewPresentationModelTest {
    @Test
    void enter_student_information() {
        FakeCreateStudentView view = new FakeCreateStudentView();
        CreateStudentViewPresentationModel pm = createStudentViewPM(view);

        pm.enterName("");
        pm.enterAge(0);
        assertFalse(view.isCreateButtonEnabled());

        pm.enterName("Mars");
        pm.enterAge(25);
        assertTrue(view.isCreateButtonEnabled());
    }

    @Test
    void create_student() {
        FakeCreateStudentView view = new FakeCreateStudentView();
        FakeAddStudentUseCase useCase = new FakeAddStudentUseCase();
        CreateStudentViewPresentationModel pm = createStudentViewPM(view, useCase);

        enterStudentInformation(pm);

        pm.createStudent();

        assertEquals(useCase.addStudentInput, new AddStudentInput("Mars", 25));
    }

    private CreateStudentViewPresentationModel createStudentViewPM(CreateStudentView view, AddStudentUseCase useCase) {
        CreateStudentViewPresentationModel result = new CreateStudentViewPresentationModel(useCase);
        result.setView(view);
        return result;
    }

    private CreateStudentViewPresentationModel createStudentViewPM(CreateStudentView view) {
        return createStudentViewPM(view, new FakeAddStudentUseCase());
    }

    private static class FakeAddStudentUseCase implements AddStudentUseCase {

        private AddStudentInput addStudentInput;

        @Override
        public UUID execute(AddStudentInput addStudentInput) {
            this.addStudentInput = addStudentInput;
            return UUID.randomUUID();
        }
    }

    private void enterStudentInformation(CreateStudentViewPresentationModel pm) {
        pm.enterName("Mars");
        pm.enterAge(25);
    }

    private static class FakeCreateStudentView implements CreateStudentView {
        private boolean isEnabled = false;
        public Boolean isCreateButtonEnabled() {
            return isEnabled;
        }

        @Override
        public void setCreateButtonEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
        }
    }
}