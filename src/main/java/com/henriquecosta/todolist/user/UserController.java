package com.henriquecosta.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/create")
  public UserModel create(@RequestBody UserModel userModel) {
    var user = this.userRepository.findByUsername(userModel.getUsername());

    if (user != null) {
      System.out.println("Usuário já existe!");
      return null;
    }
    var userCreated = this.userRepository.save(userModel);
    return userCreated;

  }
}
