package pl.coderslab.imageviewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.imageviewer.model.User;
import pl.coderslab.imageviewer.service.IUserService;
import pl.coderslab.imageviewer.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private IUserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findUsers(HttpSession session, Model model){
        if(session.getAttribute("userName") != null && !session.getAttribute("userName").toString().isEmpty()) {
            // add `message` attribute
            model.addAttribute("message", "Current user: " + session.getAttribute("userName").toString());
        } else {
            model.addAttribute("message", "No user selected");
        }

        var users = (List<User>)userService.findAll();

        model.addAttribute("users", users);

        return "users";
    }

    @PostMapping("users/deleteUser")
    public String deleteUser(@RequestParam String id){
        userService.deleteById(Integer.parseInt(id));
        return "redirect:/users";
    }
}