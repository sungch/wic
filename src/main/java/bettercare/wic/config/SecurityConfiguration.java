package bettercare.wic.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Any resources that need avoid security
     */
    @Override
    public void configure(WebSecurity ws) {
        ws.ignoring().antMatchers("/", "/wic");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); // using BCrypt
        builder.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN")
                .and()
                .withUser("wic").password(encoder.encode("wic")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/customerOrder").permitAll()
                .antMatchers("/vouchers", "/voucher").hasAnyRole("USER", "ADMIN")
                .antMatchers("/categories/**", "/category").hasAnyRole("USER", "ADMIN")
                .antMatchers("/products/**", "/product").hasAnyRole("USER", "ADMIN")
                .antMatchers("/missingProducts", "/missingProduct").hasAnyRole("USER", "ADMIN")
                .antMatchers("/deliveries", "/delivery").hasAnyRole("USER", "ADMIN")
                .antMatchers("/wicOrders/**", "/wicOrder").hasAnyRole("USER", "ADMIN")
                .antMatchers("/customers/**", "/customer").hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "/wic").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();

        http.csrf().disable();
    }
}
