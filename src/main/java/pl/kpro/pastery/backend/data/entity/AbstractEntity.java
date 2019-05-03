package pl.kpro.pastery.backend.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode()
    {
        return Objects.hash(id, version);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this==o)
            return true;
        else if(o == null || this.getClass() != o.getClass())
            return false;
        else
        {
            AbstractEntity other = (AbstractEntity) o;
            return this.version == other.version && Objects.equals(this.id, other.id);
        }
    }

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
