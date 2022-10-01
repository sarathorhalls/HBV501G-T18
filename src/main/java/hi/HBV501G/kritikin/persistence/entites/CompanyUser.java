package hi.HBV501G.kritikin.persistence.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "company_users")
public class CompanyUser {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Company company;

    private String username;
    private String password;

    public CompanyUser() {}

    public CompanyUser(Company company, String username, String password) {
        this.company = company;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
