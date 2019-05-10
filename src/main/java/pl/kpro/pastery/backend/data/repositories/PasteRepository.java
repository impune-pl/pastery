package pl.kpro.pastery.backend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;

import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Repository
public interface PasteRepository extends JpaRepository<Paste,Long>
{
    Optional<Paste> findByTitleIgnoreCase(String title);

    Optional<Paste> findByAuthorIgnoreCase(User author);

    long countByAuthorIgnoreCase(User author);
}
