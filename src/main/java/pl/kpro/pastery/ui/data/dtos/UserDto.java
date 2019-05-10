package pl.kpro.pastery.ui.data.dtos;

import pl.kpro.pastery.backend.data.AccountState;
import pl.kpro.pastery.backend.data.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

/**
 * User data transfer object, needed to use binder for binding with registration form.
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class UserDto
{
    @NotBlank
    private String username;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    private AccountState accountState;

    @NotBlank
    @Size(min = 4, max = 255)
    private String password;

    @NotBlank
    private Role role;

    @NotBlank
    private OffsetDateTime creationDate;

    @NotBlank
    private CharSequence passwordConfirmation;

    private Boolean hasAgreedToEulaAndToS;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public AccountState getAccountState()
    {
        return accountState;
    }

    public void setAccountState(AccountState accountState)
    {
        this.accountState = accountState;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public OffsetDateTime getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate)
    {
        this.creationDate = creationDate;
    }

    public CharSequence getPasswordConfirmation()
    {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(CharSequence passwordConfirmation)
    {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Boolean getHasAgreedToEulaAndToS()
    {
        return hasAgreedToEulaAndToS;
    }

    public void setHasAgreedToEulaAndToS(Boolean hasAgreedToEulaAndToS)
    {
        this.hasAgreedToEulaAndToS = hasAgreedToEulaAndToS;
    }

}
