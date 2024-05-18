package com.project.backend.security;

import com.project.backend.model.UserRole;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Authorization
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                // =========== secure endpoint ==============
                // ---------- auth ----------
                .antMatchers("/auth/login").authenticated()
                .antMatchers("/auth/updateNameAndAddress").authenticated()
                .antMatchers("/auth/updatePassword").authenticated()
                .antMatchers("/auth/getUserById/**").authenticated()
                // ---------- product ----------
                .antMatchers("/product/add").hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/product/update").hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/product/delete/**").hasAuthority(UserRole.ADMIN.name())
                // ---------- favorite ----------
                .antMatchers("/favorite/**").authenticated()
                // ---------- shoppingCart ----------
                .antMatchers("/shoppingCart/**").hasAuthority(UserRole.USER.name())
                // ---------- order ----------
                .antMatchers("/order/getAll").hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/order/updateOrderStatusUser").hasAuthority(UserRole.USER.name())
                .antMatchers("/order/updateOrderStatusAdmin").hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/order/**").authenticated()
                // ---------- review ----------
                .antMatchers("/review/getAllByProductId/**").permitAll()
                .antMatchers("/review/**").authenticated()
                // ---------- flaggedReview ----------
                .antMatchers("/flaggedReview/**").hasAuthority(UserRole.USER.name())
                // ---------- statistics ----------
                .antMatchers("/statistics/**").hasAuthority(UserRole.ADMIN.name())
                .and().httpBasic() // login
                .and().sessionManagement().maximumSessions(1) // only allow 1 session at a time
                .and().sessionCreationPolicy(SessionCreationPolicy.NEVER); // Spring security never create a HttpSession, use one if exists
    }
}
