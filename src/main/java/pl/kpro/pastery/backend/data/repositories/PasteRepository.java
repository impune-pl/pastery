package pl.kpro.pastery.backend.data.repositories;

import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;

import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface PasteRepository
{
    Optional<Paste> findByTitleIgnoreCase(String title);

    Optional<Paste> findByAuthorIgnoreCase(User author);

    long countByAuthorIgnoreCase(User author);
}
