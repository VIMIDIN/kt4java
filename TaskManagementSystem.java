import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Класс Task
class Task implements Comparable<Task> {
    private String title;
    private String description;
    private boolean isCompleted;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void complete() {
        this.isCompleted = true;
    }

    @Override
    public int compareTo(Task other) {
        return Boolean.compare(this.isCompleted, other.isCompleted);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}

// Подкласс PriorityTask
class PriorityTask extends Task {
    private String priority;

    public PriorityTask(String title, String description, String priority) {
        super(title, description);
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "PriorityTask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", isCompleted=" + isCompleted() +
                ", priority='" + priority + '\'' +
                '}';
    }
}

// Класс ImmutableTask
final class ImmutableTask {
    private final String title;
    private final String description;
    private final boolean isCompleted;

    public ImmutableTask(String title, String description, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Пример проблемы с изменением
    // public void complete() { this.isCompleted = true; } // Ошибка компиляции

    @Override
    public String toString() {
        return "ImmutableTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}

// Класс TaskManager
class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void completeTask(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                task.complete();
                break;
            }
        }
    }

    public void saveTasksToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadTasksFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            tasks = (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }
}

// Главный класс для тестирования
public class TaskManagementSystem {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Создание задач
        Task task1 = new Task("Buy groceries", "Milk, Bread, Eggs");
        Task task2 = new PriorityTask("Finish project", "Complete the Java project", "High");
        Task task3 = new Task("Read a book", "Read '1984' by George Orwell");

        // Добавление задач
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Изменение статуса задач
        taskManager.completeTask("Buy groceries");

        // Сохранение задач в файл
        taskManager.saveTasksToFile("tasks.dat");

        // Загрузка задач из файла
        TaskManager loadedTaskManager = new TaskManager();
        loadedTaskManager.loadTasksFromFile("tasks.dat");

        // Вывод загруженных задач
        for (Task task : loadedTaskManager.getTasks()) {
            System.out.println(task);
        }
    }
}

