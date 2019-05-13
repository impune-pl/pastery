package pl.kpro.pastery.backend.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kpro.pastery.backend.data.AccountState;
import pl.kpro.pastery.backend.data.Role;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.data.repositories.UserRepository;
import pl.kpro.pastery.backend.error.UserAlreadyExistsException;
import pl.kpro.pastery.backend.error.UserNotFoundException;
import pl.kpro.pastery.ui.data.dtos.UserDto;

import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Service
public class UserService implements FilterableCrudService<User>
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserRepository getRepository()
    {
        return this.userRepository;
    }

    @Override
    public User createNew(User currentUser)
    {
        return new User();
    }

//    public User activateUserAccount(User user, String activationCode)
//    {
//        User activatingUser = userRepository.findByEmailIgnoreCase(user.getEmail()).orElse(null);
//        if(activatingUser==null)
//        {
//            throw new UserNotFoundException("No user account for email: "+user.getEmail());
//        }
//        else
//        {
//            if(activatingUser.getActivationCode().equals(activationCode) && activatingUser.getAccountState() == AccountState.INACTIVE)
//            {
//                activatingUser.setAccountState(AccountState.ACTIVE);
//            }
//            else
//            {
//                user.generateActivationCode();
//
//            }
//        }
//    }

    public User registerNewUser(UserDto userDto) throws UserAlreadyExistsException
    {
        if(userRepository.findByEmailIgnoreCase(userDto.getEmail()).orElse(null) == null)
        {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
            user.setAccountState(AccountState.ACTIVE);
            user.setRole(Role.USER);
//            user.generateActivationCode();
            return getRepository().save(user);
        }
        else
            throw new UserAlreadyExistsException("Account with this email: "+userDto.getEmail()+" already exists");
    }

    @Override
    public Page<User> findAnyMatching(Optional<String> filter, Pageable pageable)
    {
        if(filter.isPresent())
        {
            String repositoryFilter = "%" + filter.get() + "%";
            return getRepository()
                    .findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrRoleLikeIgnoreCase
                            (repositoryFilter,repositoryFilter,repositoryFilter,pageable);
        }
        return find(pageable);
    }

    private Page<User> find(Pageable pageable)
    {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional filter)
    {
        if(filter.isPresent())
        {
            String repositoryFilter = "%" + filter.get() + "%";
            return getRepository()
                    .countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrRoleLike
                            (repositoryFilter,repositoryFilter,repositoryFilter);
        }
        return count();
    }
}
