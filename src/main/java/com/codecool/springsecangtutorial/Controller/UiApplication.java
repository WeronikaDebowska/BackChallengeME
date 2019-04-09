package com.codecool.springsecangtutorial.Controller;

import com.codecool.springsecangtutorial.DAO.UserRepository;
import com.codecool.springsecangtutorial.Model.User;
import com.codecool.springsecangtutorial.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "*")
@RestController
public class UiApplication {

    private MyUserDetailsService myUserDetailsService;


    @Autowired
    public UiApplication(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @RequestMapping("/login")
    public Boolean login(@RequestBody User user) {
        System.out.println("logowanie");
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user);
        System.out.println(myUserDetailsService.loadUserByUsername(user.getUserName()).getPassword().equals(user.getPassword()));
        return myUserDetailsService.loadUserByUsername(user.getUserName()).getPassword().equals(user.getPassword());

    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        System.out.println("/user");
//        System.out.println(request);
        String authToken = request.getHeader("Authorization")
                .substring("Basic ".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];

    }
}
