package pl.kpro.pastery.backend.data;

/**
 * Defines static roles for users
 * TODO: replace with dynamic roles stored in database
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public enum Role
{
    ADMIN, USER;
    public static String USER()
    {
        return USER.name();
    }

    public static String ADMIN()
    {
        return ADMIN.name();
    }
    public static String[] getAllRoles()
    {
        return new String[] {ADMIN.name(), USER.name()};
    }
}
