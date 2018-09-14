package com.vvthang.mycontact.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("IN - passwordEncoder()");
        logger.debug("OUT - passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("IN - configureGlobal()");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        logger.debug("OUT - configureGlobal()");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("IN - configure(HttpSecurity)");
        // http
        // .authorizeRequests()
        // .antMatchers("/user/**").hasRole(Role.MEMBER)
        // .antMatchers("/admin/**").hasRole(Role.ADMIN)
        // .antMatchers("/api/**").hasRole(Role.MEMBER);
        logger.debug("OUT - configure(HttpSecurity)");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.debug("IN - configure(WebSecurity)");
        web.ignoring().antMatchers("/resources/**", "/webjars/**");
        logger.debug("OUT - configure(WebSecurity)");
    }

}
