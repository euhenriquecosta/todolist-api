package com.henriquecosta.todolist.tasks;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(length = 250)
  private String description;

  @Column(length = 50)
  private String title;
  private String priority;

  private LocalDateTime startAt;
  private LocalDateTime endAt;

  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public void setTitle(String title) throws Exception {
    if (title.length() > 50) {
      throw new Exception("O campo title deve conter no máximo 50 caracteres");
    }
    this.title = title;
  }

}
