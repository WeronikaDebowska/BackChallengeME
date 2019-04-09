package com.codecool.springsecangtutorial;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration
        extends WebSecurityConfigurerAdapter {


//    @Bean(name = "dataSource")
//    public DriverManagerDataSource dataSource() {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
//        driverManagerDataSource.setUrl("jdbc:postgresql://ec2-107-20-177-161.compute-1.amazonaws.com:5432/daaoh13o9op66j?sslmode=require&user=iwnwvdnwwygsba&password=41cf65d700a225624add425426ff2ee9fb9588d96ba0fde1a58ebf1e0d1b068c");
//        driverManagerDataSource.setUsername("iwnwvdnwwygsba");
//        driverManagerDataSource.setPassword("41cf65d700a225624add425426ff2ee9fb9588d96ba0fde1a58ebf1e0d1b068c");
//        return driverManagerDataSource;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("weronika")
                .password("12345678")
                .roles("USER");

//                .jdbcAuthentication().dataSource(dataSource())
//                .usersByUsernameQuery(
//                        "select username,password from users where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, role from users where username=?");


    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
