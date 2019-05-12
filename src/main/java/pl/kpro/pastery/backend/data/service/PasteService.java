package pl.kpro.pastery.backend.data.service;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.kpro.pastery.backend.data.Role;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.data.repositories.PasteRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class PasteService implements LazyLoadableFiltrableCrudService<Paste>
{
    @Autowired
    private PasteRepository pasteRepository;

    @Override
    public PasteRepository getRepository()
    {
        return this.pasteRepository;
    }

    @Override
    public Paste save(User currentUser, Paste entity)
    {
        if(entity.getAuthor().equals(currentUser))
            return getRepository().saveAndFlush(entity);
        else
            return entity;
    }

    @Override
    public void delete(User currentUser, Paste entity)
    {
        if(entity != null && currentUser != null && (entity.getAuthor().equals(currentUser) || currentUser.getRole() == Role.ADMIN))
            getRepository().delete(entity);
    }

    @Override
    public void delete(User currentUser, long id)
    {
        if(currentUser != null)
        {
            Paste entity;
            try
            {
                entity = load(id);
            }catch(EntityNotFoundException e)
            {
                //TODO: put it in log
                return;
            }
            delete(currentUser,entity);
        }
    }

    @Override
    public Page findAnyMatching(Optional filter, Pageable pageable)
    {
        if(filter.isPresent())
        {
            String repositoryFilter = "%" + filter.get() + "%";
            return getRepository()
                    .findByTitleIgnoreCase(repositoryFilter);
        }
        return find(pageable);
    }

    private Page<Paste> find(Pageable pageable)
    {
        return getRepository().findBy(pageable);
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
        if(filter.isPresent())
        {
            String repositoryFilter = "%" + filter.get() + "%";
            return getRepository()
                    .countByTitleIgnoreCase(repositoryFilter);
        }
        return count();
    }

    public Page<Paste> findAnyOwnedBy(User currentUser)
    {
        return getRepository().findByAuthorIgnoreCase(currentUser);
    }

    @Override
    public Integer countLoadable(User currentUser)
    {
        if(currentUser != null)
            return Math.toIntExact(getRepository().countByAuthorIgnoreCase(currentUser));
        else
            return 0;
    }

    @Override
    public List<Paste> findAllBetweenAndSortedBy(int offset, int limit, Map<String,Boolean> sortOrders, User currentUser)
    {
        //TODO: implement
    }
}
