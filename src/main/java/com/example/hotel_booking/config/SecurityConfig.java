package com.example.hotel_booking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(cors ->{})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth


                        .requestMatchers("/api/auth/**").permitAll()




                        .requestMatchers(HttpMethod.POST, "/api/hotels/**")
                        .hasRole("ADMIN")


                        .requestMatchers(HttpMethod.POST, "/api/rooms/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/rooms/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**")
                        .hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/api/rooms/**")
                        .permitAll()


                        .requestMatchers(HttpMethod.POST,"/api/bookings/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.GET,"/api/bookings/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers("/api/bookings/my")
                        .hasRole("CUSTOMER")

                        .requestMatchers("/api/bookings/all")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/search/**").permitAll()

                        .requestMatchers("/api/payments/**").hasRole("CUSTOMER")

                        .requestMatchers("/api/admin/dashboard/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                )


                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}