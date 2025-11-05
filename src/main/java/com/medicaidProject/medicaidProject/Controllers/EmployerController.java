package com.medicaidProject.medicaidProject.Controllers;

import com.medicaidProject.medicaidProject.modles.Address;
import com.medicaidProject.medicaidProject.modles.Employer;
import com.medicaidProject.medicaidProject.modles.data.AddressDao;
import com.medicaidProject.medicaidProject.modles.data.EmployerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

    @Controller
    @RequestMapping("employer")

    public class EmployerController {

        @Autowired
        private EmployerDao employerDao;

        @Autowired
        private AddressDao addressDao;

        @Autowired
        private PasswordEncoder passwordEncoder;

        // -------------------- LOGIN --------------------
        @GetMapping(value = "login")
        public String loginForm(Model model) {
            model.addAttribute("employer", new Employer());
            return "employer/index";
        }

        @PostMapping("login")
        public String processLogin(@ModelAttribute Employer employer, Model model, HttpSession session){
            Employer existingEmployer = employerDao.findByEmail(employer.getEmail());

            if(existingEmployer == null || !passwordEncoder.matches(employer.getPassword(), existingEmployer.getPassword())){
                model.addAttribute("error", "Invalid username or password");
                return "employer/index";
            }

            //Successful login
            session.setAttribute("employer", existingEmployer);
            return "redirect:/employer/employer-dashboard";
        }

        // -------------------- SIGN-UP --------------------
        @GetMapping("sign-up")
        public String signUpForm(Model model) {
            model.addAttribute("title", "Sign up to get started!");
            model.addAttribute("employer", new Employer());
            return "employer/sign-up";
        }

        @PostMapping("sign-up")
        public String processSignUp(
                Model model,
                @ModelAttribute Employer employer,
                @RequestParam("address") String address,
                @RequestParam("city") String city,
                @RequestParam("state") String state,
                @RequestParam("zip") String zip,
                Errors errors,
                HttpSession session) {

           //Check if email account already exists
           if(employerDao.findByEmail(employer.getEmail()) != null){
               model.addAttribute("emailError", "Email already exists");
               model.addAttribute("email", employer.getEmail());
               return "employer/sign-up";
           }

           //Check if passwords match
            if(!employer.getPassword().equals(employer.getVerifyPassword())){
                model.addAttribute("error", "Passwords do not match.");
                return "employer/sign-up";
            }

            //Hash password before saving
            employer.setPassword(passwordEncoder.encode(employer.getPassword()));
            employerDao.save(employer);

            // Create and link Address
            Address addr = new Address();
            addr.setStreet(address);
            addr.setCity(city);
            addr.setState(state);
            addr.setZip(zip);
            addr.setEmployer(employer);  // <-- important link to employer

            addressDao.save(addr);

            //Auto login after sign-up
            session.setAttribute("employer", employer);

            return "redirect:/employer/employer-dashboard";
        }

        // -------------------- DASHBOARD --------------------
        @GetMapping("employer-dashboard")
        public String employerDashboard(HttpSession session, Model model) {
            Employer employer = (Employer) session.getAttribute("employer");

            if (employer == null) {
                return "redirect:employer/login";
            }

            model.addAttribute("employer", employer);

            //load address if it exists
            Address address = addressDao.findByEmployer(employer).orElse(null);
            model.addAttribute("address", address);
            return "employer/employer-dashboard";  // employer dashboard
        }
        //--------------------- SAVE ADDRESS USER DASHBOARD ---------------------
        @PostMapping("employer-dashboard/save")
        public String saveEmployerAndAddress(
                @RequestParam("email") String email,
                @RequestParam("phone") String phone,
                @RequestParam("firstName") String firstName,
                @RequestParam("lastName") String lastName,
                @RequestParam("employerName") String employerName,
                @RequestParam("employerTaxId") String employerTaxId,
                @RequestParam("street") String street,
                @RequestParam("city") String city,
                @RequestParam("zip") String zip,
                @RequestParam("state") String state,
                HttpSession session
        ){
            Employer employer = (Employer) session.getAttribute("employer");
            if (employer == null) return "redirect:/employer/login";

            //Update employer info
            employer.setEmail(email);
            employer.setPhone(phone);
            employer.setEmployerName(employerName);
            employer.setFirstName(firstName);
            employer.setLastName(lastName);
            employer.setEmployerTaxId(employerTaxId);

            // Save employer changes
            employerDao.save(employer);

            // Update or create address
            Address address = addressDao.findByEmployer(employer).orElse(new Address());
            address.setStreet(street);
            address.setCity(city);
            address.setZip(zip);
            address.setState(state);
            address.setEmployer(employer);

            //Save address changes
            addressDao.save(address);

            return "redirect:/employer/employer-dashboard";
        }
        // -------------------- LOGOUT --------------------
        @GetMapping("logout")
        public String logout(HttpSession session) {
            session.invalidate(); // clear the session
            return "redirect:/user/login?logout";
        }

    }


