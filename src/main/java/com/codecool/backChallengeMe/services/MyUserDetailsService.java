package com.codecool.backChallengeMe.services;

import com.codecool.backChallengeMe.DAO.UserRepository;
import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.MyUserPrincipal;
import com.codecool.backChallengeMe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.event.ChangeListener;
import java.util.List;


@Service
public class MyUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }


//    public List<Challenge> getChallengesByUserId(int id){
//        return userRepository.
//    }

}