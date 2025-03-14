package com.henriquecosta.todolist.tasks;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/create")
  public TaskModel create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var id_user = request.getAttribute("idUser");
    taskModel.setId_user((UUID) id_user);

    var task = this.taskRepository.save(taskModel);
    return task;
  }
}
