package com.project.webxaydung.configurations;

import com.project.webxaydung.Models.Role;
import com.project.webxaydung.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/user/register", apiPrefix),
                                    String.format("%s/user/login", apiPrefix)
                            )
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/candidate/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/candidate/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/candidate/**", apiPrefix)).hasAnyRole( Role.ADMIN)


                            .requestMatchers(GET,
                                    String.format("%s/category/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/category/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/category/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/category/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)


                            .requestMatchers(POST,
                                    String.format("%s/contact/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(GET,
                                    String.format("%s/contact/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/contact/**", apiPrefix)).hasAnyRole(Role.ADMIN,Role.USER)


                            .requestMatchers(POST,
                                    String.format("%s/employee/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(GET,
                                    String.format("%s/employee/**", apiPrefix)).hasRole( Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/employee/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/employee/**", apiPrefix)).hasRole(Role.ADMIN)


                            .requestMatchers(POST,
                                    String.format("%s/user/**", apiPrefix)).hasAnyRole(Role.ADMIN,Role.USER)

                            .requestMatchers(GET,
                                    String.format("%s/user/**", apiPrefix)).hasRole( Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/user/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/user/**", apiPrefix)).hasRole(Role.ADMIN)

//                            .requestMatchers(String.format("%s/jobopening/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(GET,
                                    String.format("%s/jobopening/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/jobopening/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/jobopening/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/jobopening/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)


//                            .requestMatchers(GET,
//                                    String.format("%s/new/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/new/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/new/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/new/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(GET,
                                    String.format("%s/subemail/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

//                            .requestMatchers(POST,
//                                    String.format("%s/subemail/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/subemail/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/subemail/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
//                            .anyRequest().authenticated();
                            .anyRequest().permitAll();

                })

        ;
        return http.build();
    }
}
