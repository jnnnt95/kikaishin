package com.nniett.kikaishin.app.web.security;

import com.nniett.kikaishin.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfiguration(final JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requestCustomization -> {
                            requestCustomization
                                    .requestMatchers("/api/auth/login").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/user/info").hasAnyRole(Constants.ADMIN, Constants.USER)
                                    .requestMatchers(HttpMethod.GET, "/api/user/*").hasRole(Constants.ADMIN)
                                    .requestMatchers("/api/user/disable/*").hasRole(Constants.ADMIN)
                                    .requestMatchers("/api/user/enable/*").hasRole(Constants.ADMIN)
                                    .requestMatchers(HttpMethod.DELETE,"/api/user/*").hasRole(Constants.ADMIN)
                                    .requestMatchers("/api/**").hasAnyRole(Constants.ADMIN, Constants.USER)
                                    .requestMatchers("/error").anonymous()
                                    .anyRequest().authenticated();
                        }
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
