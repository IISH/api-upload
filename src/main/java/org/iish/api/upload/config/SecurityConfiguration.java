package org.iish.api.upload.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private @Autowired LDAP ldap;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // All pages require authorization first
                .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                // Disable Cross-Site Request Forgery token
                .csrf().disable()
                // Disable HTTP Basic authentication
                .httpBasic().disable()
                // What is our login/logout page?
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldap.getUrl());
        contextSource.setUserDn(ldap.getManagerDn());
        contextSource.setPassword(ldap.getManagerPassword());
        contextSource.afterPropertiesSet();

        LdapAuthoritiesPopulator ldapAuthoritiesPopulator =
                new LdapAuthoritiesPopulator(contextSource, ldap.getSearchBase(), ldap.getAuthorized());

        authenticationManagerBuilder
                .ldapAuthentication()
                .contextSource(contextSource)
                .userSearchBase(ldap.getSearchBase())
                .userSearchFilter(ldap.getSearchFilter())
                .ldapAuthoritiesPopulator(ldapAuthoritiesPopulator);
    }
}