package pl.kpro.pastery.backend.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kpro.pastery.backend.data.Role;
import pl.kpro.pastery.backend.data.entity.User;

import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
    Optional<User> findByEmailIgnoreCase(String email);

    Page<User> findBy(Pageable pageable);

    Page<User> findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrRoleLikeIgnoreCase(
            String emailLike, String usernameLike, String roleLike, Pageable pageable);

    long countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrRoleLike(String repositoryFilter, String repositoryFilter1, String repositoryFilter2);
}
