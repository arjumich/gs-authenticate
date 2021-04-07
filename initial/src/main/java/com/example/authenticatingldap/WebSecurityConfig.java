package com.example.authenticatingldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;

@EnableWebSecurity

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=Employee")
                .groupSearchBase("ou=Employee")
                .contextSource()
                .managerDn("cn=ldapadmin,dc=klovercloud,dc=local")
                .managerPassword("password")
                .url("ldap://192.168.99.102/dc=klovercloud,dc=local")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");


/*                .ldapAuthentication()
                .contextSource()
                .url("ldap://192.168.99.102/dc=klovercloud,dc=local")
                .managerDn("cn=ldapadmin,dc=klovercloud,dc=local")
                .managerPassword("password")
                .and()
                .userSearchFilter("uid={0},ou=Employee"); */

    }
    /*@RequestMapping
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    */


}
