package com.cf.EventApp.controllers;

import com.cf.EventApp.daos.DaoException;
import com.cf.EventApp.models.Role;
import com.cf.EventApp.models.User;
import com.cf.EventApp.services.RoleService;
import com.cf.EventApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/admin")
    public String displayAdminPage(Model m) {
        m.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/addUser")
    public String addUser(String username, String password) throws DaoException {
        User toAdd = new User();
        toAdd.setUsername(username);
        toAdd.setPassword(password);
        toAdd.setEnabled(true);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.getRoleByRole("ROLE_USER"));
        toAdd.setRoles(userRoles);

        if(!userService.existsByUsername(username)) {
            userService.createUser(toAdd);
        } else {
            throw new DaoException("User already exists");
        }

        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Integer id) {

        userService.deleteUser(id);

        return "redirect:/admin";

    }

    @GetMapping("/editUser")
    public String editUserDisplay(Model model, Integer id, Integer error) {
        User user = userService.getUserById(id);
        List roleList = roleService.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roleList);

        if (error != null) {
            if (error == 1) {
                model.addAttribute("error", "Passwords did not match, password was not updated.");
            }
        }

        return "editUser";
    }

    @PostMapping(value = "/editUser")
    public String editUserAction(String[] roleIdList, Boolean enabled, Integer id) throws DaoException {
        User user = userService.getUserById(id);
        if (enabled != null) {
            user.setEnabled(enabled);
        } else {
            user.setEnabled(false);
        }

        Set<Role> roleList = new HashSet<>();
        for (String roleId : roleIdList) {
            Role role = roleService.getRoleById(Integer.parseInt(roleId));
            roleList.add(role);
        }
        user.setRoles(roleList);
        userService.updateUser(user);

        return "redirect:/admin";
    }

    @PostMapping("editPassword")
    public String editPassword(Integer id, String password, String confirmPassword) throws DaoException {
        User user = userService.getUserById(id);

        if (password.equals(confirmPassword)) {
            user.setPassword(encoder.encode(password));
            userService.updateUser(user);
            return "redirect:/admin";
        } else {
            return "redirect:/editUser?id=" + id + "&error=1";
        }
    }

}
