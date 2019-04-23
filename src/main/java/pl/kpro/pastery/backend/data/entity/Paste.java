package pl.kpro.pastery.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

/**
 * Class representing single paste
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */

@Entity
public class Paste
{
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    @Column(updatable = false)
    private ZonedDateTime creationDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    @NotBlank
    private User author;
}
