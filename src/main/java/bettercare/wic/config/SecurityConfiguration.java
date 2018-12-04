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

    // Authentication by database username
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(wicUserDetailsService())
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    // Authorization by resource path
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // <-- authorize all as follows
//                .antMatchers("**/login* ").permitAll()
//                .antMatchers("**/actuator/** ").permitAll()
//                .antMatchers("**/customerOrder/**").permitAll()
//                .antMatchers("**/vouchers/**", "**/voucher/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/categories/**", "**/category/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/products/**", "**/product/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/missingProducts/**", "**/missingProduct/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/deliveries/**", "**/delivery/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/wicOrders/**", "**/wicOrder/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/customers/**", "**/customer/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("**/admin/**").hasAnyRole("ADMIN")
//                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .httpBasic(); // this is to test wit Postman Basic authentication

        http.csrf().disable();
    }
}
