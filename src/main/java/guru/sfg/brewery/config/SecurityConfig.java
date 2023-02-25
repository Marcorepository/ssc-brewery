package guru.sfg.brewery.config;

import com.sun.xml.bind.api.impl.NameConverter;
import guru.sfg.brewery.security.OwnPasswordEncoderFactory;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter restHeaderAuthFilter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        restHeaderAuthFilter.setAuthenticationManager(authenticationManager);
        return restHeaderAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable(); // dann braucht man kein csrf-token für put,delete,post
        http
                .authorizeRequests(authorize -> {
                    authorize.antMatchers("/", "/webjars/**", "/login", "/resources/**", "/beers/find", "/beers*").permitAll();
                    // authorize.antMatchers(HttpMethod.DELETE, "/api/v1/beer/**").permitAll();
                    authorize.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll();

                    /*
                    authorize.antMatchers(HttpMethod.DELETE, "/api/v1/beer/**").permitAll();
                    authorize.antMatchers(HttpMethod.DELETE, "/api/v1/beer/*").permitAll();
                    authorize.antMatchers(HttpMethod.PATCH, "/api/v1/beer/**").permitAll();
                    authorize.antMatchers(HttpMethod.POST, "/api/v1/beer/**").permitAll();
                    authorize.antMatchers(HttpMethod.PUT, "/api/v1/beer/**").permitAll();

                     */

                    authorize.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    /*
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder().username("myUser").password("myPassword").roles("ADMIN").build();
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();

        return new InMemoryUserDetailsManager(admin, user);
    }

     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("myUser").password("{ldap}{SSHA}zS1kwAy/Ha8dzI8jPfYf7fh0AeLl0+9rOYbezA==").roles("ADMIN") //myPassword
                .and()
                .withUser("user").password("{bcrypt}$2a$15$hugZAQQmmCEvAyV5qMhB3uXrIIsWKQDimb.Nki6HXVdIjuG4p0Xv2").roles("USER") //password
                .and()
                .withUser("scott").password("{bcrypt12}$2a$12$tq3773Ri0AU2DlLrumj75eEQ3y10hr9KbEn2Mdd862eFZsu6rpzF6").roles("CUSTOMER") // tiger
                .and()
                .withUser("spring").password("{bcrypt}$2a$11$Qn5fC3hpvvKH0V4TBONDyehobc./Dhrx8KD8czFhha9fFOEe.bJAe").roles("ADMIN");
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return OwnPasswordEncoderFactory.createPasswordEncoder();
    }
}
