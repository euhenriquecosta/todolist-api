package com.henriquecosta.todolist.tasks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.henriquecosta.todolist.user.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final IUserRepository IUserRepository;

  @Autowired
  private ITaskRepository taskRepository;

  TaskController(IUserRepository IUserRepository) {
    this.IUserRepository = IUserRepository;
  }

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var id_user = request.getAttribute("id_user");
    taskModel.setIdUser((UUID) id_user);

    var currentDate = LocalDateTime.now();
    if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("A data de inicio / término deve ser maior do que a data atual!");
    }

    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("A data de inicio deve ser maior do que a data de término");
    }

    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @GetMapping("/list")
  public List<TaskModel> list(HttpServletRequest request) {
    var id_user = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser((UUID) id_user);

    return tasks;
  }
}
