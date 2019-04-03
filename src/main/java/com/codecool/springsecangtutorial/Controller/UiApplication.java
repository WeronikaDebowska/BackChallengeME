package com.codecool.springsecangtutorial.Controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "*")
@RestController
public class UiApplication {

    private static final Map<String, String> users = new HashMap<>();

    @RequestMapping("/login")
    public Boolean login(@RequestBody User user) {
        return
                user.getUserName().equals("ala") && user.getPassword().equals("kot");
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic ".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];

    }
}
