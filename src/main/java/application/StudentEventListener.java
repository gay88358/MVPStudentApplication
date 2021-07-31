package application;

import java.util.UUID;

public interface StudentEventListener {
    void studentCreated(UUID studentId, String name, Integer age);
    void studentUpdated(UUID id, String name, Integer age);
}
