package pl.kpro.pastery.backend.data.eunm;

/**
 * Simple enum for user account states:
 * ACTIVE - user can log in and operate
 * BANNED - user cannot log-in because of BAN, appropriate message should be displayed
 * STANDBY_FOR_VERIFICATION - user cannot log-in, appropriate message should be displayed along with
 * confirmation form ("enter code from email here" type) if he tries to
 *
 * @author Krzysztof 'impune_pl' Prorok
 */
public enum UserAccountState
{
    ACTIVE, BANNED, STANDBY_FOR_VERIFICATION;
}
