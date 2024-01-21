package mk.ukim.finki.wp.jan2022.g1.service.impl;

import mk.ukim.finki.wp.jan2022.g1.model.Task;
import mk.ukim.finki.wp.jan2022.g1.model.TaskCategory;
import mk.ukim.finki.wp.jan2022.g1.model.User;
import mk.ukim.finki.wp.jan2022.g1.model.exceptions.InvalidTaskIdException;
import mk.ukim.finki.wp.jan2022.g1.repository.TaskRepository;
import mk.ukim.finki.wp.jan2022.g1.service.TaskService;
import mk.ukim.finki.wp.jan2022.g1.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService
{
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public List<Task> listAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(InvalidTaskIdException::new);
    }

    @Override
    public Task create(String title, String description, TaskCategory category, List<Long> assignees, LocalDate dueDate) {
        List<User> users=assignees.stream().map(id->this.userService.findById(id)).collect(Collectors.toList());
        Task task=new Task(title,description,category,users,dueDate);
        return this.taskRepository.save(task);
    }

    @Override
    public Task update(Long id, String title, String description, TaskCategory category, List<Long> assignees) {
        List<User> users=assignees.stream().map(ids->this.userService.findById(ids)).collect(Collectors.toList());
        Task task=findById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setCategory(category);
        task.setAssignees(users);
        return this.taskRepository.save(task);
    }

    @Override
    public Task delete(Long id) {
        Task task=findById(id);
        this.taskRepository.delete(task);
        return task;
    }

    @Override
    public Task markDone(Long id) {
        Task task=findById(id);
        task.setDone(true);
        return this.taskRepository.save(task);
    }

    @Override
    public List<Task> filter(Long assigneeId, Integer lessThanDayBeforeDueDate) {
        if(assigneeId==null && lessThanDayBeforeDueDate==null){
            return listAll();
        }else if(assigneeId!=null && lessThanDayBeforeDueDate!=null){
            User user=this.userService.findById(assigneeId);
            LocalDate date=LocalDate.now().plusDays(lessThanDayBeforeDueDate);
            return this.taskRepository.findByAssigneesContainsAndDueDateBefore(user,date);
        }else if(assigneeId!=null){
            User user=this.userService.findById(assigneeId);
            return this.taskRepository.findByAssigneesContains(user);
        }else {
            LocalDate date=LocalDate.now().plusDays(lessThanDayBeforeDueDate);
            return this.taskRepository.findByDueDateBefore(date);
        }
    }
}
