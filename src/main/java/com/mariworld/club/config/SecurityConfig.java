package com.mariworld.club.config;

import com.mariworld.club.security.handler.ClubLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.authorizeRequests()
               .antMatchers("/sample/member").hasRole("USER")
                .antMatchers("/sample/admin").hasRole("ADMIN")
                .antMatchers("/sample/**").permitAll()
                .and()*/
        http
                .formLogin()
                .and()
                .rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService())
                .and()
                .csrf().disable()
                .oauth2Login().successHandler(clubLoginSuccessHandler())
                .and()
                .logout();
    }
}
