package co.turing.security;

import co.turing.error.CustomAuthFailure;
import co.turing.error.RestAccessDeniedHandler;
import co.turing.error.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http.formLogin().failureHandler(customAuthenticationFailureHandler());
        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .antMatchers(HttpMethod.POST, "/customers").permitAll()//
                .antMatchers("/login").permitAll()//
                .antMatchers("/categories/**").permitAll()
                .antMatchers("/departments/**").permitAll()
                .antMatchers("/attributes/**").permitAll()
                .antMatchers("/products/**").permitAll()
                .antMatchers("/shoppingcart/**").permitAll()
                .antMatchers("/tax/**").permitAll()
                .antMatchers("/shipping/**").permitAll()

                // Disallow everything else..
                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        // If a user try to access a resource without having enough permissions
        //http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        // Optional, if you want to test the API from a browser
        // http.httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

//    @Bean
//    RestAccessDeniedHandler accessDeniedHandler() {
//        return new RestAccessDeniedHandler();
//    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthFailure();
    }

    @Bean
    RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

}
