package pl.kpro.pastery.backend.error;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public final class UserAlreadyExistsException extends RuntimeException
{
    public UserAlreadyExistsException(final String message)
    {
        super(message);
    }
}
