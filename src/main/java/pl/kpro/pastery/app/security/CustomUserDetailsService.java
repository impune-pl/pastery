package pl.kpro.pastery.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.repositories.UserRepository;

import java.util.Collections;

/**
 * Custom User provider for SpringSecurity
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Service
@Primary //Preferred choice when multiple possibilities exist for @Autowired to choose from
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmailIgnoreCase(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("No user found with email: "+username);
        }
        else
        {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(),
                                                                          Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        }
    }
}
