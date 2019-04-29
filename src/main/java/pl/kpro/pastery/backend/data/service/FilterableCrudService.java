package pl.kpro.pastery.backend.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kpro.pastery.backend.data.entity.AbstractEntity;

import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T>
{
    public Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

    public long countAnyMatching(Optional<String> filter);
}
