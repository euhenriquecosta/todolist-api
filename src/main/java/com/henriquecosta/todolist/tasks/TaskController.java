package com.henriquecosta.todolist.tasks;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @SuppressWarnings("rawtypes")
  @PostMapping("/create")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var id_user = request.getAttribute("idUser");
    taskModel.setIdUser((UUID) id_user);

    var currentDate = LocalDateTime.now();

    if (taskModel.getStartAt() == null || taskModel.getEndAt() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("As datas de início e término são obrigatórias!");
    }

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

  @SuppressWarnings("rawtypes")
  @PutMapping("/update/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request,
      @PathVariable UUID id) {
    var idUser = (UUID) request.getAttribute("idUser");

    var tasks = this.taskRepository.findByIdUser(idUser);
    var task = tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);

    if (task == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Tarefa não encontrada ou não pertence ao usuário!");
    }

    if (!task.getIdUser().equals(idUser)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Você não tem permissão para atualizar esta tarefa!");
    }

    updateNonNullFields(taskModel, task);

    taskModel.setId(id);
    taskModel.setIdUser((UUID) idUser);

    return ResponseEntity.status(HttpStatus.OK).body(this.taskRepository.save(task));
  }

  private void updateNonNullFields(Object source, Object target) {
    for (Field field : source.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(source);
        if (value != null) {
          field.set(target, value);
        }
      } catch (IllegalAccessException e) {
        System.err.printf("Erro ao acessar o campo: {}", field.getName(), e);
      }
    }
  }
}
