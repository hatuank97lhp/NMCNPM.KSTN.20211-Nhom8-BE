package com.example.cnpmbe.security.config;


import com.example.cnpmbe.common.Constants;
import com.example.cnpmbe.jwt.AuthenUserService;
import com.example.cnpmbe.jwt.JwtAuthenticationFilter;
import com.example.cnpmbe.service.UserService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {

    @Autowired
    private AuthenUserService authenUserService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(authenUserService) // Cung cáp userservice cho spring security
                .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        security.cors().configurationSource(request -> {
//            CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowedOrigins(Arrays.asList("http://localhost:8000", "http://127.0.0.1:8080", EnvironmentConfig.BASE_URL, "*"));
//            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            configuration.setAllowedHeaders(Arrays.asList("*"));
//            return configuration;
//        }).and()
//                .csrf().disable();

        http
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Arrays.asList("http://localhost:8000", "http://127.0.0.1:8000", "*"));
                    cors.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(Arrays.asList("*"));
                    return cors;
                }).and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.POST, Constants.AUTH_LOGIN_URI).permitAll()
                .antMatchers(HttpMethod.POST, Constants.AUTH_REGISTER_URI).permitAll()
//            .antMatchers("api/auth/**", "api/oauth2/**").permitAll()
                .anyRequest().authenticated();

        // filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}

