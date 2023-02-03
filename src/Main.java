import Entity.*;
import Service.TaskService;

import Exception.IncorrectArgumentException;
import Entity.OneTimeTask;
import Entity.DailyTask;
import Entity.WeeklyTask;
import Entity.MounthlyTask;
import Entity.YearlyTask;
import Exception.TaskNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final TaskService taskService = new TaskService();

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} ");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static void main(String[] args) throws IncorrectArgumentException {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.println("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                            break;

                        case 3:
                            printTaskByDay(scanner);
                            break;

                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println(" Выберите пункт меню из списка!");
                }

            }
        }
    }



    private static void inputTask(Scanner scanner)  {
        scanner.useDelimiter("\n");

        String title = inputTaskTitle(scanner);
        String description = inputTaskDescription(scanner);
        TaskType type = inputTaskType(scanner);
        LocalDateTime taskTime = inputTaskTime(scanner);
        int repeatability = inputRepeatable(scanner);
        createTask(title,description,type,taskTime, repeatability);

    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Введите id задачи на удаление");
        int id = scanner.nextInt();

        try {
            taskService.remove(id);
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
    private static void printTaskByDay(Scanner scanner) {
        System.out.println("Введите дату в формате dd.MM.yyyy");

        if (scanner.hasNext(DATE_PATTERN)) {
            String dateTime = scanner.next(DATE_PATTERN);
            LocalDate inputDate =  LocalDate.parse(dateTime, DATE_FORMATTER);

            Collection<Task> taskByDay = taskService.getAllByDate(inputDate);

            for (Task task: taskByDay) {
                System.out.println(task);
            }

        } else {
            System.out.println("Введите дату задачи в формате dd.MM.yyyy");
            scanner.close();
        }
    }

    private static String inputTaskTitle(Scanner scanner) {
        System.out.println("Введите название задачи: ");
        String title = scanner.next();

        if (title.isBlank()) {
            System.out.println("Ошибка");
            scanner.close();
        }

        return title;
    }

    private static String inputTaskDescription(Scanner scanner) {
        System.out.println("Введите описание задачи: ");
        String description = scanner.next();

        if (description.isBlank()) {
            System.out.println("Ошибка");
            scanner.close();
        }

        return description;
    }

    private static TaskType inputTaskType(Scanner scanner) {
        System.out.println("Введите тип задачи (1 - личная задача, 2 - рабочая) ");
        TaskType type = null;

        int taskTypeChoice = scanner.nextInt();

        switch (taskTypeChoice) {
            case 1:
                type = TaskType.PERSONAL;
                break;
            case 2:
                type = TaskType.WORK;
                break;
            default:
                System.out.println("Ошибка");
                scanner.close();
        }
        return type;
    }

    private static LocalDateTime inputTaskTime(Scanner scanner) {
        System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm");

        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);

        } else {
            System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm");
            scanner.close();
            return null;
        }

    }

    private static int inputRepeatable (Scanner scanner) {
        System.out.println("Введите повтояемость задачи (1 - однократно, 2 - каждый день, 3 - каждую неделю, " +
                "4 - каждый месяц, 5 - кадлый год)");

        if (scanner.hasNextInt()) {
            return scanner.nextInt();

        } else {
            System.out.println("Ошибка");
            scanner.close();
        }

        return -1;

    }
    private static void createTask(String title, String description, TaskType type, LocalDateTime taskTime, int repeatability) {
        Task task = null;

        try {
            switch (repeatability) {
                case 1:
                    task = new OneTimeTask(title, description, type, taskTime);
                    break;
                case 2:
                    task = new DailyTask(title, description, type, taskTime);
                    break;
                case 3:
                    task = new WeeklyTask(title, description, type, taskTime);
                    break;
                case 4:
                    task = new MounthlyTask(title, description, type, taskTime);
                    break;
                case 5:
                    task = new YearlyTask(title, description, type, taskTime);
                    break;
                default:
                    System.out.println("Повторяемость задачи введена неккоректно");
            }

        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage());
        }


        if (task != null) {
            taskService.add(task);
            System.out.println("Задача добавлена");
        } else {
            System.out.println("Ошибка");
        }
    }




    private static void  printMenu() {
        System.out.println("1. Добавить задачу\n 2. Удалить задачу\n 3. Получить задачу на указанный день\n 0.Выход");
    }
}

