package pl.kpro.pastery.backend.data.entity;

import pl.kpro.pastery.backend.data.AccountState;
import pl.kpro.pastery.backend.data.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing User.
 * Contains following information about user:
 * - unique, automatically generated id for database
 * - username
 * - unique email (max length of 255 chars)
 * - sha-256-hashed password (min 4 characters, max 255 characters)
 * - information about role (user/admin)
 * - list of pasted created by user
 * @author Krzysztof 'impune_pl' Prorok
 */
@Entity
public class User extends AbstractEntity
{

    @NotBlank // must contain at least one non-whitespace character and not be null
    private String username;

    @Email
    @NotBlank
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountState accountState;

    @NotBlank
    @Size(min = 4, max = 255)
    private String passwordHash;

    //TODO: put roles in dedicated table, and use list instead of enum.
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(updatable = false)
    private OffsetDateTime creationDate;

//    @NotBlank
//    private String activationCode;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paste> pastes;

    public User()
    {
        this.creationDate = OffsetDateTime.now();
        this.pastes=new ArrayList<>();
    }

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
        this.email = email.toLowerCase();
    }

    public AccountState getAccountState()
    {
        return accountState;
    }

    public void setAccountState(AccountState accountState)
    {
        this.accountState = accountState;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
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

    public List<Paste> getPastes()
    {
        return pastes;
    }

    public void setPastes(List<Paste> pastes)
    {
        this.pastes = pastes;
    }


//    public String getActivationCode()
//    {
//        return activationCode;
//    }
//
//    public void setActivationCode(String activationCode)
//    {
//        this.activationCode = activationCode;
//    }
}
