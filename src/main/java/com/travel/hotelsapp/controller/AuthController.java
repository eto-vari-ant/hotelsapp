package com.travel.hotelsapp.controller;

import com.travel.hotelsapp.model.User;
import com.travel.hotelsapp.repository.UserRepository;
import com.travel.hotelsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Wrong password or email");
        }
        return "login";
    }


    @GetMapping("registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addClient(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                            Model model)  {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userService.isEmailExist(user.getEmail())) {
            model.addAttribute("emailError", "Client with this email exists");
            return "registration";
        }
        if (!userService.save(user)) {
            return "registration";
        }
        return "redirect:/login";
    }
}
