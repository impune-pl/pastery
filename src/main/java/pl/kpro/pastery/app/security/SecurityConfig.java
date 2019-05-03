package pl.kpro.pastery.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import pl.kpro.pastery.backend.data.Role;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.data.repositories.UserRepository;

/**
 * TODO: allow access to robots txt, favicon, and other static resources
 * TODO: implement token-based access to shared pastes - handle it in vaadin, but spring security should check if token is correct.
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService)
    {
        this.userDetailsService=userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CurrentUser currentUser(UserRepository userRepository) {
        final String username = SecurityUtils.getUsername();
        User user = userRepository.findByEmailIgnoreCase(username).orElse(null);
        return () -> user;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                    .requestMatchers(SecurityUtils::isVaadinInternalRequest).permitAll()
                    .regexMatchers("/").permitAll()
                    .regexMatchers("/user/.*").hasAuthority(Role.USER.name())
                    .regexMatchers("/admin/.*").hasAuthority(Role.ADMIN.name())
                    .and().formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login")
                                                                      .failureUrl("/login/error")
                                                                      .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                    .requiresChannel()
                        .regexMatchers(".*").requiresSecure()
        ;
    }
}
