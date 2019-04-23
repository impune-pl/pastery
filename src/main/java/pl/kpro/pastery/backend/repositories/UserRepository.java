package pl.kpro.pastery.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kpro.pastery.backend.data.entity.User;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface UserRepository extends JpaRepository<User,Long>
{
    User findByEmailIgnoreCase(String email);

    Page<User> findBy(Pageable pageable);

    Page<User> findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrRoleLikeIgnoreCase(
            String emailLike, String usernameLike, String roleLike, Pageable pageable);
}
