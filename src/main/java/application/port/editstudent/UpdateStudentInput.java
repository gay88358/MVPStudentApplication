package application.port.editstudent;

import java.util.UUID;

public class UpdateStudentInput {
    private final UUID id;
    private final String name;
    private final Integer age;

    public UpdateStudentInput(UUID id, String name, Integer age) {

        this.id = id;
        this.name = name;
        this.age = age;
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
}
