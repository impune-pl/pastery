package pl.kpro.pastery.backend.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Repository
public interface PasteRepository extends JpaRepository<Paste,Long>
{
    Page<Paste> findByTitleIgnoreCase(String titleLike);

    Page<Paste> findByAuthorIgnoreCase(User author);

    long countByAuthorIgnoreCase(User author);

    long countByTitleIgnoreCase(String titleLike);

    Page<Paste> findBy(Pageable pageable);
}
