package Exception;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(Integer taskId) {
        super("Задачв с id = " + taskId + " не найдена");
    }

}
