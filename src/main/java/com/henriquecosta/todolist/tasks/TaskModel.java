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
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(length = 250)
  private String description;

  @Column(length = 50)
  private String title;
  private String priority;

  private LocalDateTime start_at;
  private LocalDateTime end_at;

  private UUID id_user;

  @CreationTimestamp
  private LocalDateTime created_at;

}
