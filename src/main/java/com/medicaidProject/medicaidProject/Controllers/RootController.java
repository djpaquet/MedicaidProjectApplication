/*Developed by Dana Paquet*/
package com.medicaidProject.medicaidProject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RootController {

    @RequestMapping("/")
    public String showLandingPage() {
        return "root/index";  // templates/root/index.html
    }

}




