package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.CompanyUser;

public interface CompanyUserService {
    public void save(CompanyUser companyUser);
    public void delete(CompanyUser companyUser);
    public CompanyUser findById(long id);
    public CompanyUser findByUsername(String username);
    public List<CompanyUser> findAll();
}
