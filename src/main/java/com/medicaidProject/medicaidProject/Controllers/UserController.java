/*Developed by Dana Paquet*/
package com.medicaidProject.medicaidProject.Controllers;

import com.medicaidProject.medicaidProject.modles.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")

public class UserController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("user", new User());
        return "user/index";
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.GET)
    public String sign_up(Model model){
        model.addAttribute(new User());
        model.addAttribute("title", "Sign up to get started!");
        return "user/sign-up";
    }

    @RequestMapping(value="sign-up", method = RequestMethod.POST)
    public String processSignUp(Model model,@ModelAttribute User user,Errors errors,HttpSession session) {

        session.setAttribute("user", user);
        return "redirect:/user/user-dashboard";
    }


    @GetMapping("user-dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/user/sign-up";
        }

        model.addAttribute("user", user);
        return "user/user-dashboard";
    }

    @GetMapping("user-employment-verification-form")
    public String employmentVerification(HttpSession session, Model model) {
            User user = (User) session.getAttribute("user");

            if (user == null) {
                return "redirect:/user/sign-up"; // or show an error page
            }

            model.addAttribute("user", user);
            return "user/user-employment-verification-form";
        }

}