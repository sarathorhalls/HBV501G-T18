package hi.HBV501G.kritikin.persistence.entites;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EAuthority name;

    public Authority() {
    }

    public Authority(EAuthority name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EAuthority getName() {
        return name;
    }

    public void setName(EAuthority name) {
        this.name = name;
    }
}
