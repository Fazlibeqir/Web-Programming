package mk.ukim.finki.wp.jan2022.g1.repository;

import mk.ukim.finki.wp.jan2022.g1.model.Task;
import mk.ukim.finki.wp.jan2022.g1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>
{
    List<Task> findByAssigneesContainsAndDueDateBefore(User user, LocalDate dueDate);

    List<Task> findByDueDateBefore(LocalDate date);

    List<Task> findByAssigneesContains(User user);
}
