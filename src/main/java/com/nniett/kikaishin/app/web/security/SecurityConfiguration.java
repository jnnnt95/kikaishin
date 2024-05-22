package com.nniett.kikaishin.app.web.security;

import com.nniett.kikaishin.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfiguration(final JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
        logger.info("Kikaishin's SecurityConfiguration initialized.");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Kikaishin's security filter setup init.");
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requestCustomization -> {
                            requestCustomization
                                    .requestMatchers(HttpMethod.GET, "/doc").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
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
        logger.debug("Kikaishin's security filter setup ready for build.");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Password encoder requested.");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        logger.debug("Authentication manager requested.");
        return configuration.getAuthenticationManager();
    }
}
