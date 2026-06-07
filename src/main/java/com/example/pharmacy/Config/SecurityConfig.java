package com.example.pharmacy.Config;

import com.example.pharmacy.Security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    // SECURITY RULES
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC API

                        .requestMatchers("/uploads/**").permitAll()
// ១. សិទ្ធិទូទៅ (Public Access)
                                .requestMatchers("/api/auth/**").permitAll()



                                // ២. USER MANAGEMENT (Admin តែម្នាក់គត់)

                                .requestMatchers("/api/users/**").hasRole("ADMIN")

                                // ៣. PRODUCT (Admin កែសម្រួល, Cashier គ្រាន់តែមើល)
                                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

                                // ៤. ORDER & ORDER ITEMS (Cashier និង Admin អាចលក់បាន)
                                .requestMatchers(HttpMethod.GET, "/api/orders/**", "/api/order-items/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.POST, "/api/orders/**", "/api/order-items/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.PUT, "/api/orders/**", "/api/order-items/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.DELETE, "/api/orders/**", "/api/order-items/**").hasAnyRole("ADMIN", "CASHIER")


                                // ៥. PAYMENT (Cashier អាចបង្កើត/មើល, Admin កែ/លុប)
                                .requestMatchers(HttpMethod.GET, "/api/payments/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.POST, "/api/payments/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.PUT, "/api/payments/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/payments/**").hasRole("ADMIN")

                                // ៦. INVOICE (Cashier អាចមើល, Admin គ្រប់គ្រង)
                                .requestMatchers(HttpMethod.GET, "/api/invoices/**").hasAnyRole("ADMIN", "CASHIER")
                                .requestMatchers(HttpMethod.POST, "/api/invoices/**").hasAnyRole("ADMIN", "CASHIER")
//
                                .requestMatchers(HttpMethod.PUT, "/api/invoices/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/invoices/**").hasRole("ADMIN")

                                // ៧. CATEGORIES & BRANDS (Admin គ្រប់គ្រង)
                                .requestMatchers("/api/categories/**", "/api/brands/**").hasRole("ADMIN")
                                .requestMatchers("/api/categories/**", "/api/categories/**").hasRole("ADMIN")

                                // ៨. គ្រប់យ៉ាងត្រូវ Login
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}