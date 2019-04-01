package pl.kpro.pastery.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.kpro.pastery.backend.data.eunm.Role;

/**
 * TODO: allow access to robots txt, favicon, and other static resources
 * TODO: implement custom user service
 * TODO: implement user registration
 * TODO: implement password encryption
 * TODO: implement token-based access
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
            .regexMatchers("/").hasAnyAuthority(Role.getAllRoles())
            .regexMatchers("/admin/.*").hasAuthority(Role.ADMIN)
            .and().formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login").failureUrl("/login?error")
            .and().logout().logoutSuccessUrl("/")
        ;
    }
}
