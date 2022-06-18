package com.peak.training.account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;


    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

               //.and()
           // http
                    .csrf().disable()
                .authorizeRequests()
                //user endpoint can access ADMIN and USER ROLES
                //.antMatchers("/api/*", "/peaktraining/*").permitAll()
                //.antMatchers("/registeration/**").permitAll()
                .antMatchers("/api/**", "/peaktraining/**").authenticated()

                //can access all users
                //.antMatchers("/").permitAll()
                .anyRequest().permitAll()

                .and().formLogin().permitAll()
                .defaultSuccessUrl("/registeration/successful")
                .and()
                .logout(logout->
                        logout.clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll());
    }


    /*@Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

}