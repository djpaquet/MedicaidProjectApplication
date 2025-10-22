package com.medicaidProject.medicaidProject.Controllers;

import com.medicaidProject.medicaidProject.modles.Employer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

    @Controller
    @RequestMapping("employer")

    public class EmployerController {

        @RequestMapping(value = "index", method = RequestMethod.GET)
        public String employerIndex(Model model) {
            model.addAttribute("employer", new Employer());
            return "employer/index";
        }

        // @RequestMapping(value = "", method = RequestMethod.POST)
        //  public String processEmployerLogin(Model model, @ModelAttribute Employer employer, HttpSession session) {
        //      session.setAttribute("employer", employer);
        //      return "redirect:/employer/employer-dashboard";
        //  }

        @RequestMapping(value = "", method = RequestMethod.GET)
        public String index(Model model) {
            model.addAttribute("employer", new Employer());
            return "employer/index";
        }

        @RequestMapping(value = "sign-up", method = RequestMethod.GET)
        public String sign_up(Model model){
            model.addAttribute(new Employer());
            model.addAttribute("title", "Sign up to get started!");
            return "employer/sign-up";
        }

        @RequestMapping(value="sign-up", method = RequestMethod.POST)
        public String processSignUp(Model model, @ModelAttribute Employer employer, @RequestParam("verifyPassword") String verifyPassword, Errors errors, HttpSession session) {
            if (!verifyPassword.equals(employer.getPassword())) {
                model.addAttribute("error", "Passwords do not match.");
               return "employer/sign-up";
            }
            session.setAttribute("employer", employer);
            System.out.println("Employer saved to session: " + session.getAttribute("employer"));

            return "redirect:/employer/employer-dashboard";
        }

        @GetMapping("employer-dashboard")
        public String employerDashboard(HttpSession session, Model model) {
            Employer employer = (Employer) session.getAttribute("employer");

            if (employer == null) {
                return "redirect:employer/sign-up";
            }

            model.addAttribute("employer", employer);
            return "employer/employer-dashboard";  // employer dashboard
        }
    }


