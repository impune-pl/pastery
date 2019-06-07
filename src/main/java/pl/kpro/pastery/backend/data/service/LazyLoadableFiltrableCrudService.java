package pl.kpro.pastery.backend.data.service;

import pl.kpro.pastery.backend.data.entity.AbstractEntity;
import pl.kpro.pastery.backend.data.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface LazyLoadableFiltrableCrudService<T extends AbstractEntity> extends FilterableCrudService<T>
{

    Integer countLoadable(User currentUser);

    List<T> findAllBetweenAndSortedByAndOwnedBy(int offset, int limit, Map<String, Boolean> sortOrders,
                    User currentUser);
}
