package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Company;

public interface CompanyService {
    public void save(Company company);

    public void delete(Company company);

    public Company findById(long id);

    public Company findByName(String name);

    public List<Company> findAll();

    public Company getReferenceById(long id);
}
