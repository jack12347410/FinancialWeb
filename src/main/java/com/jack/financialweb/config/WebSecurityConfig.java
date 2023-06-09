package com.jack.financialweb.config;

import com.jack.financialweb.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())//關掉csrf保護
//                .authorizeHttpRequests(auth->
//                        auth.anyRequest().permitAll());//讓所有頁面都不用登入

        http.csrf(csrf -> csrf.disable())//關掉csrf保護
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//No Session
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers(new AntPathRequestMatcher("/api/auth/**")).anonymous()
//                            .anyRequest().authenticated()
//                )
                .addFilterBefore(new AuthorizationFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }
}
