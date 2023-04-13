package hi.HBV501G.kritikin.controllers;

import java.net.URI;
import java.util.Base64;

/**
 * This class is the controller for everythin related to companies. It handles
 * the creation, deletion and updating of companies from REST requests.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.entites.DTOs.CompanyDTO;
import hi.HBV501G.kritikin.persistence.entites.DTOs.ReviewJSON;
import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.ReviewService;
import hi.HBV501G.kritikin.services.UserService;

@RestController
@CrossOrigin()
public class CompanyController {

    public static final String APIURL = "/api";

    Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;
    private ReviewService reviewService;
    private UserService userService;

    /**
     * Constructor for the CompanyController which uses Autowired to inject the
     * CompanyService from JPA
     * 
     * @param companyService
     */
    @Autowired
    public CompanyController(CompanyService companyService, ReviewService reviewService, UserService userService) {
        this.companyService = companyService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    /**
     * Returns a list of all companies as a json object from a get request to
     * /api/companies .
     * 
     * @return a list of all companies
     */
    @GetMapping(value = APIURL + "/companies")
    public ResponseEntity<List<Company>> fetchAllcompanies() {
        return ResponseEntity.ok().body(companyService.findAll());
    }

    /**
     * Returns a particular company as a json object from a get request to
     * /api/companies/{id} which has the id of the company in the path.
     * 
     * @param id the id of the company
     * @return a particular company
     */
    @GetMapping(value = APIURL + "/company/{id}")
    public ResponseEntity<Company> company(@PathVariable long id) {
        Company foundCompany = companyService.findById(id);
        List<ReviewJSON> reviews = reviewService.findByCompany(id);
        
        if (reviews.isEmpty()) {
            foundCompany.setStarRating(5.0);
        } else {
            double allRatings = 0;
            for (ReviewJSON reviewJSON : reviews) {
                allRatings += reviewJSON.getStarRating();
            }
            foundCompany.setStarRating(allRatings / reviews.size());
        }
        companyService.save(foundCompany);
        return ResponseEntity.ok().body(foundCompany);
    }

    @GetMapping(value = APIURL + "/findCompany/{name}")
    public ResponseEntity<List<Company>> findCompany(@PathVariable String name) {
        return ResponseEntity.ok().body(companyService.findMultipleByName(name));
    }

    /**
     * Deletes a particular company from a delete request to /api/companies/{id}
     * which has the id of the company in the path.
     * 
     * @param id the id of the company to be deleted
     * @return the deleted company
     */
    @DeleteMapping(value = APIURL + "/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return null;
        }
        companyService.delete(company);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a company to the database from a post request to /api/company with the
     * company to be inserted in the body.
     * 
     * @param company the company to be inserted, fetched from the body of the post
     *                request
     * @return the inserted company or null if the company already exists
     */
    @PostMapping(value = APIURL + "/company")
    public ResponseEntity<Company> addCompany(Company company) {
        if (company == null || company.getName() == null || company.getName().equals("")) {
            return ResponseEntity.badRequest().body(null);
        }
        if (companyService.existsByName(company.getName())) {
            return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIURL + "/company").toUriString());
        return ResponseEntity.created(uri).body(companyService.save(company));

    }

    @PatchMapping(value = APIURL + "/company/{id}")
    public ResponseEntity<?> updateCompany(CompanyDTO updatedCompany, @PathVariable("id") long companyId, @RequestHeader("Authorization") String auth) {
        logger.info("addQuestion() called with authorization header: {}", auth);
        String token = auth.replace("Bearer ", "").split("\\.")[1];
        String decodedPayload = new String(Base64.getDecoder().decode(token));
        Long userId;
        try {
            userId = Long.parseLong(decodedPayload.split(",")[1].split(":")[1].split("\"")[1]);
            logger.info("User id: {}", userId);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(CompanyController.APIURL + "/company/" + companyId + "/question").toUriString());
        } catch (Exception e) {
            logger.error("Error parsing user id from token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (companyId != userService.getReferenceById(userId).getManagedCompany().getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Company company = companyService.findById(companyId);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        if (updatedCompany.getName() != null && !updatedCompany.getName().equals("")) {
            company.setName(updatedCompany.getName());
        }
        if (updatedCompany.getAddress() != null && !updatedCompany.getAddress().equals("")) {
            company.setAddress(updatedCompany.getAddress());
        }
        if (!(updatedCompany.getPhoneNumber() == 0)) {
            company.setPhoneNumber(updatedCompany.getPhoneNumber());
        }
        if (updatedCompany.getWebsite() != null && !updatedCompany.getWebsite().equals("")) {
            company.setWebsite(updatedCompany.getWebsite());
        }
        companyService.save(company);
        return ResponseEntity.noContent().build();
    }
}
