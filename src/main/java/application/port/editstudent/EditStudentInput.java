package application.port.editstudent;

import java.util.Objects;
import java.util.UUID;

public class EditStudentInput {
    private final UUID id;
    private final String name;
    private final Integer age;

    public EditStudentInput(UUID id, String name, Integer age) {

        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EditStudentInput that = (EditStudentInput) object;
        return Objects.equals(id.toString(), that.id.toString()) &&
                Objects.equals(name, that.name) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
