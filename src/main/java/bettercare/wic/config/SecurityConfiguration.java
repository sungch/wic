package bettercare.wic.config;

import bettercare.wic.service.WicUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@EnableGlobalMethodSecurity(prePostEnabled = true) // -> This is for @PreAuthorize("hasAnyRole('ADMIN')")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public WicUserDetailsService wicUserDetailsService() {
        return new WicUserDetailsService();
    }

    // Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(wicUserDetailsService()).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    // Authorization
     @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("**/login* ").permitAll()
                .antMatchers("**/actuator/** ").permitAll()
                .antMatchers("**/customerOrder/**").permitAll()
                .antMatchers("**/products/**", "**/product/**").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .logout().permitAll();

        http.csrf().disable();
    }

}
