package application.port.getstudents;

import java.util.Objects;
import java.util.UUID;

public class StudentDto {
    private final UUID id;
    private final String name;
    private final Integer age;

    public StudentDto(UUID id, String name, Integer age) {

        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StudentDto that = (StudentDto) object;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
