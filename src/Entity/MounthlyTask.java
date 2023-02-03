package Entity;

import java.time.LocalDateTime;
import Exception.IncorrectArgumentException;

public class MounthlyTask extends Task{

    public MounthlyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusMonths(1);
    }
}
