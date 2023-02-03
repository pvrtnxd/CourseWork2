package Entity;

import java.time.LocalDateTime;
import Exception.IncorrectArgumentException;

public class WeeklyTask extends Task{


    public WeeklyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusWeeks(1);
    }
}
