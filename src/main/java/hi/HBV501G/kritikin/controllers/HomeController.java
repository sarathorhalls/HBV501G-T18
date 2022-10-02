package hi.HBV501G.kritikin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    public static final String APIURL = "/api";

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
