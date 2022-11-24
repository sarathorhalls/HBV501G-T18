package hi.HBV501G.kritikin.services.implementation;

/**
 * This class is the implementation of the CompanyService interface. It handles
 * all business logic to and from the repositories for everything regarding
 * companies.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.repositories.CompanyRepository;
import hi.HBV501G.kritikin.services.CompanyService;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;

    Logger logger = LoggerFactory.getLogger(CompanyServiceImplementation.class);

    /**
     * Constructor for CompanyServiceImplementation which uses Autowired to inject
     * companyRepository from JPA.
     * 
     * @param companyRepository
     */
    @Autowired
    public CompanyServiceImplementation(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

        // this.save(new Company("Test", 5.0, "https://test.com", 1234567, "Test description", "Test address", "Test opening hours"));
        // this.save(new Company("Test2", 4.0, "https://test2.com", 1234567, "Test2 description", "Test2 address", "Test2 opening hours"));
        // this.save(new Company("Sportís", 5.0, "https://sportis.is", 5201000, "Sportís er rótgróið fyrirtæki á sviði íþrótta á Íslandi. Við höfum verið til starfa frá árinu 1983 og vöndum vel valið á merkjum sem við bjóðum uppá.", "Skeifan 11", "Sunnudagur: ekki opið, Mánudagur: 10-18, Þriðjudagur: 10-18, Miðvikudagur: 10-18, Fimmtudagur: 10-18, Föstudagur: 10-18, Laugardagur: 11-16"));
        // this.save(new Company("Sports Direct", 5.0, "https://sportsdirect.com", 5713000, "Við höfum aldrei verið hrædd við að stefna fram á við og breyta iðnaðinum, auka fjölbreytni okkar og reisa verslanir.", "Skogarlind 2, 201 Kópavogur", "Sunnudagur: 11-19, Mánudagur: 10:30-19, Þriðjudagur: 10:30-19, Miðvikudagur: 10:30-19, Fimmtudagur: 10:30-19, Föstudagur: 10:30-19, Laugardagur: 10-19"));
        // this.save(new Company("Studio Sport", 5.0, "https://studiosport.is", 4821120, "Fyrir allar þínar sport þarfir á selfossi", "Austurvegur 9, 800 Selfoss", "Sunnudagur: ekki opið, Mánudagur: 10-18, Þriðjudagur: 10-18, Miðvikudagur: 10-18, Fimmtudagur: 10-18, Föstudagur: 10-18, Laugardagur: 10-16"));
        // this.save(new Company("Raf og tæknilausnir", 5.0, "https://rogt.is", 8400952, "Raf og tæknilausnir ehf. er fyrirtæki sem hefur verið til staðar í 4 ár og við setjum okkur það markmið að setja gæði og fagmennsku í fyrirrúm.", "Álfhólsvegur 118, 200 Kópavogur", "Sunnudagur: ekki opið, Mánudagur: 8-17, Þriðjudagur: 8-17, Miðvikudagur: 8-17, Fimmtudagur: 8-17, Föstudagur: 8-17, Laugardagur: ekki opið"));
        // this.save(new Company("Raf ehf", 5.0, "https://rafehf.is", 4626400, "Raf var stofnað 1980 og var upphaflega staðsett á Akureyri en fluttist alfarið til Hafnarfjarðar 2016. Frá árinu 2020 hefur Raf verið með útibú í Grundarfirði.", "Gjótuhrauni 8, 220 Hafnarfjordur", "Sunnudagur: ekki opið, Mánudagur: 8-17, Þriðjudagur: 8-17, Miðvikudagur: 8-17, Fimmtudagur: 8-17, Föstudagur: 8-17, Laugardagur: ekki opið"));
        // this.save(new Company("Rafstilling", 5.0, "http://rafstilling.is", 5814991, "Rafstilling ehf er sérhæft í viðgerðum og sölu á alternatorum og störturum. Áratuga reynsla af þessari starfsemi nýtist okkur til að veita hraða og góða þjónustu við allt landið.", "Dugguvogur, 104 Reykjavík", "Sunnudagur: ekki opið, Mánudagur: 8-17, Þriðjudagur: 8-17, Miðvikudagur: 8-17, Fimmtudagur: 8-17, Föstudagur: 8-14, Laugardagur: ekki opið"));
        // this.save(new Company("Bílaraf", 5.0, "https://bilaraf.is", 5640400, "Bílaraf var stofnað árið 1964 og byggðist upp á sölu og viðgerðum á störturum og alternatorum. Í seinni tíð hefur fyrirtækið haslað sér völl með þjónustu fyrir allar gerðir ferðavagna ásamt almennum bílaviðgerðum.", "Flatahraun 25, 220 Hafnarfjörður", "Sunnudagur: ekki opið, Mánudagur: 9-16, Þriðjudagur: 9-16, Miðvikudagur: 9-16, Fimmtudagur: 9-16, Föstudagur: 9-16, Laugardagur: ekki opið"));

    }

    /**
     * Saves a company to the database.
     * 
     * @param company the company to be saved.
     */
    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    /**
     * Deletes a company from the database.
     * 
     * @param company the company to be deleted.
     */
    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    /**
     * Returns a company from the database with a given id.
     * 
     * @param id the id of the company to be returned.
     * @return the company with the given id.
     */
    @Override
    public Company findById(long id) {
        return companyRepository.findById(id);
    }

    /**
     * Returns a company from the database with a given name.
     * 
     * @param name the name of the company to be returned.
     * @return the company with the given name.
     */
    @Override
    public boolean existsByName(String name) {
        Streamable<Company> companies = companyRepository.findByNameIgnoreCaseContains(name);
        return !companies.isEmpty();
    }

    @Override
    public List<Company> findMultipleByName(String name) {
        return companyRepository.findByNameIgnoreCaseContains(name).toList();
    }

    /**
     * Returns a list of all companies in the database.
     * 
     * @return a list of all companies in the database.
     */
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Returns a reference to a particular company as an object in the database with
     * the given id.
     * 
     * @param id the id of the company to be returned.
     * @return a reference to the company with the given id.
     */
    @Override
    public Company getReferenceById(long id) {
        return companyRepository.getReferenceById(id);
    }
}
