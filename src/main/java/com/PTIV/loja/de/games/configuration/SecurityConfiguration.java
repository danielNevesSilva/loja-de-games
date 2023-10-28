package com.PTIV.loja.de.games.configuration;
import com.PTIV.loja.de.games.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }
    //authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/resources/**", "/static/**", "/images/**", "/Images/**", "/prescription/**", "/css/**", "/js/**")
                        .permitAll()
                        .requestMatchers( "/").permitAll()
                        .requestMatchers( "/console").permitAll()
                        .requestMatchers( "/shop/**").permitAll()
                        .requestMatchers( "/register").permitAll()
                        .requestMatchers("/profile/**").hasRole("CUSTOMER")
                        .requestMatchers("/payment").hasRole("CUSTOMER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"));

        return http.build();
    }

    //configuration for password encoder
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**"
                , "static/**"
                , "/images/**"
                , "/Images/**"
                , "/prescription/**"
                , "/css/**"
                , "/js/**");
    }
}