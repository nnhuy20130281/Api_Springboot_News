package vn.edu.hcmuaf.apiNews.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.edu.hcmuaf.apiNews.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    //beans
    //bcrypt bean definition

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //authenticationProvider bean definition

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET, "/api/cate").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/cate").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/cate/hidden").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/cate/hidden/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/cate/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/cate/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/cate/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/admin").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/admin/lock").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/lock").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/lock/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/bookmark/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/api/users/bookmark/{idUser}/{idNews}").hasRole("USER")

                                .requestMatchers(HttpMethod.GET, "/api/news").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/news").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/news/cate/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/news/hidden").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/news/hidden/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/news/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/news/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/news/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/change-password").authenticated()
                                .anyRequest().permitAll()
        );



        // use HTTP Basic authentication
        http.httpBasic();

        // disable Cross Site Request Forgery (CSRF)
        // in general not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf().disable();

        return http.build();
    }
}
