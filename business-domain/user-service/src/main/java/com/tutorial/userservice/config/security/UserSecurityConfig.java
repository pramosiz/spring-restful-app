package com.tutorial.userservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig {

        /**
         * It solves authentication and authorization for the user-service, but not
         * cross-site request forgery (CSRF) protection yet (disabled)
         * 
         * @param http: HttpSecurity
         * @return SecurityFilterChain
         * @throws Exception: Exception
         */
        // @Bean
        // public SecurityFilterChain filterChainV1(HttpSecurity http) throws Exception
        // {

        // http.csrf(csrf -> csrf.disable())
        // .authorizeHttpRequests(authorize -> authorize
        // .anyRequest().authenticated())
        // .formLogin(Customizer.withDefaults())
        // .httpBasic(Customizer.withDefaults());
        // return http.build();
        // }

        // @formatter:off
        // private static final String[] NO_AUTH_LIST = {
        //         "/v3/api-docs/**",
        //         "/swagger-ui/**",
        //         "/swagger-resources/**",
        //         "/configuration/security",
        //         "/configuration/ui",
        //         "webjars/**",
        //         "/login",
        //         "/h2-console/**"
        // };

        // @Bean
        // public SecurityFilterChain filterChainV2(HttpSecurity http) throws Exception {

        //         http.csrf(csrf -> csrf.disable())
        //                 .authorizeHttpRequests(authorize -> authorize
        //                         .requestMatchers(NO_AUTH_LIST).permitAll()
        //                         .requestMatchers(HttpMethod.POST, "/api/v2/user/**").authenticated()
        //                         .requestMatchers(HttpMethod.GET, "/api/v2/user/**").hasRole("ADMIN"))
        //                 .formLogin(Customizer.withDefaults())
        //                 .httpBasic(Customizer.withDefaults());
        //         return http.build();
        // }
        // // @formatter:on

        @Bean
        protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
                return http.build();
        }
}
