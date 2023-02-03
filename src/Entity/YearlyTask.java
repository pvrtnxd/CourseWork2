package Entity;

import java.time.LocalDateTime;
import Exception.IncorrectArgumentException;

public class YearlyTask extends Task{


    public YearlyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusYears(1);
    }
}
