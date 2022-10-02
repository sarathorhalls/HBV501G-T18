package hi.HBV501G.kritikin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hi.HBV501G.kritikin.services.CompanyService;

@Controller
public class HomeController {

    private CompanyService companyService;

    @Autowired
    public HomeController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
