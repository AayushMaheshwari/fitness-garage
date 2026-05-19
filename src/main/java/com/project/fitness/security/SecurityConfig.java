package com.project.fitness.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity
public class SecurityConfig {

    // @Autowired
    // private DataSource datasource;

    // @Autowired
    // private AuthTokenFilter authTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/recommendations/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/recommendations/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/user/register").permitAll()
                                .anyRequest().authenticated());
        // http.addFilterBefore(authTokenFilter,
        // UsernamePasswordAuthenticationFilter.class);
        // .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user1 = User.withUsername("admin1")
    // .password(passwordEncoder().encode("adminpassword1"))
    // .roles("ADMIN")
    // .build();

    // UserDetails user2 = User.withUsername("user1")
    // .password(passwordEncoder().encode("userpassword1"))
    // .roles("USER")
    // .build();

    // //return new InMemoryUserDetailsManager(user1, user2);
    // JdbcUserDetailsManager userDetailsManager = new
    // JdbcUserDetailsManager(datasource);
    // if (!userDetailsManager.userExists(user1.getUsername())) {
    // userDetailsManager.createUser(user1);
    // }
    // if (!userDetailsManager.userExists(user2.getUsername())) {
    // userDetailsManager.createUser(user2);
    // }
    // return userDetailsManager;
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public AuthenticationManager
    // authenticationManager(AuthenticationConfiguration authConfig) throws
    // Exception {
    // return authConfig.getAuthenticationManager();
    // }
}
