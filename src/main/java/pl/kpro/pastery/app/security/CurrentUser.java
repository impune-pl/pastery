package pl.kpro.pastery.app.security;

import pl.kpro.pastery.backend.data.entity.User;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@FunctionalInterface
public interface CurrentUser
{
    User getUser();
}
