/* Developed by Dana Paquet */
package com.medicaidProject.medicaidProject.Controllers;

import com.medicaidProject.medicaidProject.modles.Address;
import com.medicaidProject.medicaidProject.modles.States;
import com.medicaidProject.medicaidProject.modles.User;
import com.medicaidProject.medicaidProject.modles.UserEmploymentInfo;
import com.medicaidProject.medicaidProject.modles.data.AddressDao;
import com.medicaidProject.medicaidProject.modles.data.StatesDao;
import com.medicaidProject.medicaidProject.modles.data.UserDao;
import com.medicaidProject.medicaidProject.modles.data.UserEmploymentInfoDao;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEmploymentInfoDao userEmploymentInfoDao;

    @Autowired
    private StatesDao statesDao;

    public List<States> getStates() {
        return statesDao.findAll();
    }

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

        // Check if email already exists
        if (userDao.findByPin(user.getPin()) != null) {
            model.addAttribute("userPinError", "Duplicate PIN detected. Account with that PIN already exists");
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

        model.addAttribute("states", getStates());

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
            Model model,
            HttpSession session) {

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) return "redirect:/user/login";

        // Validate state selection
        if (state == null || state.isBlank()) {
            model.addAttribute("stateError", "Please select a state.");
            model.addAttribute("states", statesDao.findAll());

            // Reload address so form keeps values
            Address address = addressDao.findByUserId(sessionUser.getId()).orElse(new Address());
            model.addAttribute("address", address);

            return "user/user-dashboard";
        }
        // Re-fetch managed instance
        User user = userDao.findById(sessionUser.getId()).orElseThrow();

        // ----------------- PIN uniqueness check -----------------
        User existingUserWithPin = userDao.findByPin(pin);
        if (existingUserWithPin != null && !existingUserWithPin.getId().equals(user.getId())) {
            model.addAttribute("pinError", "This PIN is already in use by another user.");
            model.addAttribute("user", user);
            model.addAttribute("states", statesDao.findAll());
            Address address = addressDao.findByUserId(user.getId()).orElse(new Address());
            model.addAttribute("address", address);
            return "user/user-dashboard";  // show form with error
        }

        // ----------------- Update user info -----------------
        user.setEmail(email);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(LocalDate.parse(dateOfBirth));
        user.setPin(pin);
        userDao.save(user);

        // ----------------- Update or create address -----------------
        Address address = addressDao.findByUserId(user.getId()).orElse(new Address());
        address.setStreet(street);
        address.setCity(city);
        address.setZip(zip);
        address.setState(state);
        address.setUser(user);
        addressDao.save(address);

        // Update session user
        session.setAttribute("user", user);

        return "redirect:/user/user-dashboard";
    }

    // -------------------- EMPLOYMENT VERIFICATION FORM --------------------
    @GetMapping("user-employment-verification-form")
    public String employmentVerification(
            HttpSession session,
            Model model,
            @RequestParam(value = "edit", required = false) String edit
    ) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/user/login";
        }

        User user = userDao.findById(sessionUser.getId()).orElseThrow();
        UserEmploymentInfo info = userEmploymentInfoDao.findByUserId(user.getId());

        boolean editMode;

        if (info == null) {
            // No info → editable by default
            editMode = true;
            info = new UserEmploymentInfo();
        } else if (Boolean.TRUE.equals(info.getIsVerified())) {
            // Verified → read-only, cannot edit
            editMode = false;
        } else {
            // Not verified → read-only initially
            editMode = false;
            // Enable edit if user clicked "Edit"
            if (edit != null) {
                editMode = true;
            }
        }


        model.addAttribute("states", statesDao.findAll());
        model.addAttribute("userEmploymentInfo", info);
        model.addAttribute("editMode", editMode);

        return "user/user-employment-verification-form";
    }

    @PostMapping("user-employment-verification-form")
    public String saveEmploymentInfo(
            @Valid @ModelAttribute("userEmploymentInfo") UserEmploymentInfo formInfo,
            BindingResult errors,
            HttpSession session,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/user/login";
        }

        User user = userDao.findById(sessionUser.getId())
                .orElseThrow();

        UserEmploymentInfo existing = userEmploymentInfoDao.findByUserId(user.getId());

        if (existing != null && Boolean.TRUE.equals(existing.getIsVerified())) {
            return "redirect:/user/user-employment-verification-form?locked";
        }

        // Validation failed → return form
        if (errors.hasErrors()) {
            model.addAttribute("editMode", true); // allow editing
            model.addAttribute("userEmploymentInfo", formInfo); // <-- must keep this line
            model.addAttribute("states", statesDao.findAll());
            return "user/user-employment-verification-form";
        }

        if (existing == null) {
            existing = new UserEmploymentInfo();
            existing.setUser(user);
        }

        existing.setEmployerName(formInfo.getEmployerName());
        existing.setEmployerTaxId(formInfo.getEmployerTaxId());
        existing.setTin(formInfo.getTin());
        existing.setAddress(formInfo.getAddress());
        existing.setCity(formInfo.getCity());
        existing.setState(formInfo.getState());
        existing.setZip(formInfo.getZip());
        existing.setCountry(formInfo.getCountry());
        existing.setIsCurrentlyEmployed(formInfo.getIsCurrentlyEmployed());
        existing.setHoursWorked(formInfo.getHoursWorked());
        existing.setEmploymentLength(formInfo.getEmploymentLength());
        existing.setNotes(formInfo.getNotes());
        existing.setCertified(formInfo.getCertified() != null ? formInfo.getCertified() : false);
        existing.setIsVerified(false);

        userEmploymentInfoDao.save(existing);

        return "redirect:/user/user-employment-verification-form?saved";
    }

    // -------------------- LOGOUT --------------------
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear the session
        return "redirect:/user/login?logout";
    }
}
