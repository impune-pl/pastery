package pl.kpro.pastery.backend.data.eunm;

/**
 * defines static roles for users
 * TODO: replace with dynamic role system
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class Role
{
    public static final String ADMIN = "admin";
    public static final String USER = "user";

    public static String[] getAllRoles()
    {
        return new String[] {ADMIN, USER};
    }
}
