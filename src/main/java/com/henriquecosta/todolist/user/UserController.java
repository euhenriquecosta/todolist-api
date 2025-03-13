package com.henriquecosta.todolist.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @PostMapping("/create")
  public void create(@RequestBody UserModel userModel) {
    System.out.printf(
        "As informações de usuário são as seguintes:\n\nName: %s\nUsername: %s\nPassword: %s",
        userModel.getName(), userModel.getUsername(), userModel.getPassword());
  }
}
