package com.cf.EventApp.controllers;

import com.cf.EventApp.repo.DaoException;
import com.cf.EventApp.models.Role;
import com.cf.EventApp.models.User;
import com.cf.EventApp.services.RoleService;
import com.cf.EventApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    Set<ConstraintViolation<User>> violations = new HashSet<>();

    @GetMapping("/login")
    public String showLoginForm() {
        if (isAuthenticated()) {
            return "redirect:home";
        }
        return "login";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @GetMapping("/account")
    public String displayContentPage(HttpServletRequest request, Model model) {
        // need to grab the user id from the parameter
        int userId = Integer.parseInt(request.getParameter("id"));
        User currentUser = userService.getUserById(userId);

        model.addAttribute("user", currentUser);

        return "account";
    }

    @GetMapping("/createAccount")
    public String createAccount(Model model) {
        User blankUser = new User();
        blankUser.setId(0L);
        blankUser.setUsername("");
        blankUser.setFirstName("");
        blankUser.setLastName("");
        blankUser.setPassword("");
        blankUser.setPhoneNumber("");
        Set<Role> roles = new HashSet<>();
        Role blankRole = new Role();
        roles.add(blankRole);
        blankUser.setRoles(roles);

        model.addAttribute("user", blankUser);
        model.addAttribute("errors", violations);
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public String createAccount(@Valid User addedUser, BindingResult result, Model model) {

        if (result.hasErrors()) {

            model.addAttribute("user", addedUser);
            model.addAttribute("errors", result.getAllErrors());
            return "createAccount";
        } else {

            try {
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(roleService.getRoleByRole("ROLE_USER"));
                addedUser.setRoles(userRoles);

                addedUser.setPassword(encoder.encode(addedUser.getPassword()));
                userService.createUser(addedUser);

                return "redirect:/home";
            } catch (DaoException ex) {
                FieldError error = new FieldError("user", "username", "Username already exists. Please choose a unique username.");
                result.addError(error);
                model.addAttribute("errors", result.getAllErrors());
                return "createAccount";
            }
        }
    }
}
