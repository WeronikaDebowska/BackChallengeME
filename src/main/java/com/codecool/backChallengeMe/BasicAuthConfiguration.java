package com.codecool.backChallengeMe;

import com.codecool.backChallengeMe.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration
        extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetails;

    @Autowired
    public BasicAuthConfiguration(MyUserDetailsService userDetails) {
        this.userDetails = userDetails;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http
                .cors().and()
                .csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/logout", "/loginpage").permitAll();

        http
                .authorizeRequests()
                .and()
                .formLogin()
                .loginProcessingUrl("/authorization")
                .failureUrl("/loginpage")

                .defaultSuccessUrl("/user")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

        http.authorizeRequests().antMatchers("/user", "/login").authenticated();


    }
}

