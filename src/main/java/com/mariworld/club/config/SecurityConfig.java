package com.mariworld.club.config;

import com.mariworld.club.security.filter.ApiCheckFilter;
import com.mariworld.club.security.filter.ApiLoginFilter;
import com.mariworld.club.security.handler.ClubLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter("/notes/**/*");
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        return apiLoginFilter;
    }

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
        http.formLogin();
        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService());
        http.csrf().disable();
        http.oauth2Login().successHandler(clubLoginSuccessHandler());
        http.logout();

        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
