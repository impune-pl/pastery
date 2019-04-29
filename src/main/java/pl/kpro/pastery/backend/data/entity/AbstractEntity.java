package pl.kpro.pastery.backend.data.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */

@MappedSuperclass
public class AbstractEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    protected void setVersion(Long version)
    {
        this.version=version;
    }

    public Long getVersion()
    {
        return version;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
