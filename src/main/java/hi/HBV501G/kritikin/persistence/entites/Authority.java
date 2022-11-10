package hi.HBV501G.kritikin.persistence.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Authority() {
    }

    public Authority(String name) {
        this.name = name;
    }

    public Authority(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
