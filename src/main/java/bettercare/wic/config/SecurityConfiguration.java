package bettercare.wic.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true) // -> This is for @PreAuthorize("hasAnyRole('ADMIN')")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity ws) {
        ws.ignoring().antMatchers("/actuator/**");
    }

    /**
     *
     * @param builder builder
     * @throws Exception exception
     */
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
        http.authorizeRequests() // <-- authorize all as follows
                .antMatchers("**/actuator/** ").permitAll()
                .antMatchers("**/customerOrder/**").permitAll()
                .antMatchers("**/vouchers/**", "**/voucher/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("**/categories/**", "**/category/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("**/products/**", "**/product/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("**/missingProducts/**", "**/missingProduct/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("**/deliveries/**", "**/delivery/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("**/wicOrders/**", "**/wicOrder/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("**/customers/**", "**/customer/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated() // allow any request
                .and()
                .formLogin().permitAll() // if /login has been created, then .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll();

        http.csrf().disable();
    }
}

/**
 * See https://www.youtube.com/watch?v=egXtoL5Kg08
 *
 Use userDetailService in place of inMemoryAuthentication to make service to call Database.
 @Autowired CustomUserDetailService u;
@Override
 protected void configure(AuthenticationManagerBuilder builder) throws Exception {
   builder.userDetailsService(u).passwordEncoder();
}

 class MyService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username){
        // load user from DB and instantiate CustomUserDetail.
        return CustomerUserDetail;
    }
}

class CustomerUserDetail extends MyUserEntity implements UserDetails {


 }

 */