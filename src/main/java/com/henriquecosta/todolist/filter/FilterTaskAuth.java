package com.henriquecosta.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.henriquecosta.todolist.user.IUserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var servletPath = request.getServletPath();

    if (servletPath.startsWith("/tasks/")) {
      // Pegar a autorização

      var authorization = request.getHeader("Authorization");

      // Tira o Basic do código da autorização
      var authEncoded = authorization.substring("Basic".length()).trim();

      // Decodifica o código da autorização e transforma em string
      byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
      var authString = new String(authDecoded);

      // Divide em duas partes, usuario e senha
      String[] credentials = authString.split(":");

      String username = credentials[0];
      String password = credentials[1];

      // Valida o usuário

      var user = this.userRepository.findByUsername(username);
      if (user == null) {
        response.sendError(401);
      } else {
        var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (result.verified) {
          // Segue viagem
          request.setAttribute("idUser", user.getId());
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }

      }
    } else {
      filterChain.doFilter(request, response);
    }

  }

}
