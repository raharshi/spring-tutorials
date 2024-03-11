package com.untangle.springtutorials.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.untangle.springtutorials.filter.JwtFilter;
import com.untangle.springtutorials.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // for Authorization levels
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    public UserDetailsService userDetailsService() {
        /*
         * Basic user details for testing
         */
        /*
            UserDetails admin = User.withUsername("Admin")
                .password(passwordEncoder.encode("Pwd1")).roles("ADMIN").build();

            UserDetails user = User.withUsername("User")
                    .password(passwordEncoder.encode("Pwd2")).roles("USER").build();

            return new InMemoryUserDetailsManager(admin, user);
         */
        return new UserService();
            
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
         * Basic form login with inmemory user details and DB
         */
        // return http.csrf(csrf -> csrf.disable())
        //         .authorizeHttpRequests(auth -> auth
        //                 .requestMatchers("/actuator/**, /user/authenticate").permitAll()
        //                 // .requestMatchers("/user/**").authenticated())
        //                 .requestMatchers("/user/**").permitAll())
        //         .formLogin(Customizer.withDefaults()).build();

        /*
        * JWT filter
        */
        
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**", "/user/authenticate").permitAll()
                        .requestMatchers("/user/**").authenticated())
                        // .requestMatchers("/user/**").permitAll())
                        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();

    }

    // Basic database login config
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // JWT config
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
