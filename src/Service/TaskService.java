package Service;

import Entity.Task;
import Exception.TaskNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TaskService {
    private final Map<Integer, Task> taskMap = new HashMap<>();

    public void add(Task task) {
        this.taskMap.put(task.getId(), task);
    }

    public void remove(Integer taskId) throws TaskNotFoundException {
        if (this.taskMap.containsKey(taskId)) {
            this.taskMap.remove(taskId);
        } else {
            throw new TaskNotFoundException(taskId);
        }
    }

    public Collection<Task> getAllByDate (LocalDate date) {
        Collection<Task> tasksByDay = new ArrayList<>();
        Collection<Task> allTasks = taskMap.values();

        for (Task task: allTasks) {
            LocalDateTime currentDateTime = task.getTaskTime();

            if (currentDateTime.toLocalDate().equals(date)) {
                tasksByDay.add(task);
                break;
            }

            LocalDateTime taskNextTime = currentDateTime;
            do{
                taskNextTime = task.getTaskNextTime(taskNextTime);

                if (taskNextTime == null) {
                    break;
                }

                if (taskNextTime.toLocalDate().equals(date)) {
                    tasksByDay.add(task);
                    break;
                }
            } while (taskNextTime.toLocalDate().isBefore(date));
        }
        return tasksByDay;
    }
}

