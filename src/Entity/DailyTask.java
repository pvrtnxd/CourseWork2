package Entity;

import java.time.LocalDateTime;
import Exception.IncorrectArgumentException;

public class DailyTask extends Task{


    public DailyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusDays(1);
    }
}
