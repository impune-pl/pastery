package pl.kpro.pastery.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Class representing User.
 * Contains following information about user:
 * - unique, automatically generated id for database
 * - username
 * - unique email (max length of 255 chars)
 * - sha-256-hashed password (min 4 characters, max 255 characters)
 * - information about role (user/admin) TODO: editable roles with permissions
 * TODO: Have I Been PWNed sha check? or at least well known passwords check like nextcloud's
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

    @NotBlank
    @Size(min = 4, max = 255)
    private String passwordHash;

    //TODO: create role field: string or role entity or enum ? choose one, find best option
}
