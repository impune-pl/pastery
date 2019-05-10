package pl.kpro.pastery.backend.data.service;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kpro.pastery.backend.data.entity.AbstractEntity;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.data.repositories.PasteRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class PasteService implements FilterableCrudService
{
    @Autowired
    private PasteRepository pasteRepository;

    @Override
    public PasteRepository getRepository()
    {
        return this.pasteRepository;
    }

    public List<Paste> findAllBetween(long offset, long limit)
    {
        return null;
    }

    @Override
    public Page findAnyMatching(Optional filter, Pageable pageable)
    {
        return null;
    }

    public long count()
    {
        return 0;
    }

    public int countInt()
    {
        return 0;
    }

    @Override
    public Paste createNew(User currentUser)
    {
        Paste newPaste = new Paste();
        newPaste.setAuthor(currentUser);
        return newPaste;
    }

    @Override
    public long countAnyMatching(Optional filter)
    {
        return 0;
    }

    public List<Paste> findAll(int offset, int limit, Map<String, Boolean> sortOrders)
    {
        return null;
    }
}
