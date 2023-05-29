package com.getbridge.homework.rest.auth;

import com.getbridge.homework.rest.config.MyServletRequestWrapper;
import com.getbridge.homework.rest.entity.User;
import com.getbridge.homework.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    UserRepository repository;

    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, MyServletRequestWrapper request) {


        Optional<User> user = repository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            throw new IllegalStateException("User not found");
        } else if (user.get().getPassword().equals(loginRequest.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("globalId", user.get().getId());
        }

    }

    @PostMapping("/logout")
    public void login(MyServletRequestWrapper request) {
            HttpSession session = request.getSession();
            session.setAttribute("globalId", "");

    }

}
