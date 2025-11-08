/* Developed by Dana Paquet */
package com.medicaidProject.medicaidProject.Controllers;

import com.medicaidProject.medicaidProject.modles.Address;
import com.medicaidProject.medicaidProject.modles.User;
import com.medicaidProject.medicaidProject.modles.data.AddressDao;
import com.medicaidProject.medicaidProject.modles.data.UserDao;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------------------- LOGIN --------------------
    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/index"; // login page
    }

    @PostMapping("login")
    public String processLogin(@ModelAttribute User user, Model model, HttpSession session) {
        User existingUser = userDao.findByUsername(user.getUsername());

        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            model.addAttribute("error", "Invalid username or password");
            return "user/index"; // show login page again with error
        }

        // Successful login
        session.setAttribute("user", existingUser);
        return "redirect:/user/user-dashboard";
    }

    // -------------------- SIGN-UP --------------------
    @GetMapping("sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Sign up to get started!");
        return "user/sign-up";
    }

    @PostMapping("sign-up")
    public String processSignUp(Model model, @ModelAttribute User user, Errors errors, HttpSession session) {

        // Check if username already exists
        if (userDao.findByUsername(user.getUsername()) != null) {
            model.addAttribute("userNameError", "Username is already taken");
            model.addAttribute("user", user); // preserve form data
            return "user/sign-up";
        }

        // Check if email already exists
        if (userDao.findByEmail(user.getEmail()) != null) {
            model.addAttribute("userEmailError", "Account with that email already exists");
            model.addAttribute("user", user); // preserve form data
            return "user/sign-up";
        }

        // Check if passwords match
        if (!user.getPassword().equals(user.getVerifyPassword())) {
            model.addAttribute("passwordError", "Passwords do not match.");
            model.addAttribute("user", user);
            return "user/sign-up";
        }

        // Validation errors
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            return "user/sign-up";
        }
        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);

        // Auto-login after sign-up
        session.setAttribute("user", user);

        return "redirect:/user/user-dashboard";
    }

    // -------------------- DASHBOARD --------------------
    @GetMapping("user-dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return "redirect:/user/login";
        }

        // Re-fetch from DB to ensure entity is managed and up to date
        User user = userDao.findById(sessionUser.getId()).orElse(sessionUser);
        model.addAttribute("user", user);

        Address address = addressDao.findByUserId(user.getId()).orElse(null);
        model.addAttribute("address", address);

        return "user/user-dashboard";
    }

    //--------------------- SAVE ADDRESS USER DASHBOARD ---------------------
    @PostMapping("user-dashboard/save")
    public String saveUserAndAddress(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("pin") String pin,
            @RequestParam("street") String street,
            @RequestParam("city") String city,
            @RequestParam("zip") String zip,
            @RequestParam("state") String state,
            HttpSession session) {

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/user/login";

        // Re-fetch managed instance
        User user = userDao.findById(sessionUser.getId()).orElseThrow();

        // Update user info
        user.setEmail(email);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(LocalDate.parse(dateOfBirth));
        user.setPin(pin);
        userDao.save(user);

        // Update or create address
        Address address = addressDao.findByUserId(user.getId()).orElse(new Address());
        address.setStreet(street);
        address.setCity(city);
        address.setZip(zip);
        address.setState(state);
        address.setUser(user);
        addressDao.save(address);

        session.setAttribute("user", user);
        return "redirect:/user/user-dashboard";
    }

    // -------------------- EMPLOYMENT VERIFICATION FORM --------------------
    @GetMapping("user-employment-verification-form")
    public String employmentVerification(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("user", user);
        return "user/user-employment-verification-form";
    }

    // -------------------- LOGOUT --------------------
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear the session
        return "redirect:/user/login?logout";
    }
}
