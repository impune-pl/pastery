package pl.kpro.pastery.backend.data.entity;

import pl.kpro.pastery.backend.data.AccountState;
import pl.kpro.pastery.backend.data.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
public class User
{
    @Id // unique id for
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    //TODO: rethink putting roles in dedicated table, and using relation instead of string.
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    @Column(updatable = false)
    private OffsetDateTime creationDate;

    public User()
    {
        this.creationDate = OffsetDateTime.now();
        this.pastes=new ArrayList<>();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paste> pastes;
}
