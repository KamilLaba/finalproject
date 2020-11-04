package pl.coderslab.imageviewer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.imageviewer.model.User;
import pl.coderslab.imageviewer.service.IUserService;
import pl.coderslab.imageviewer.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    Logger logger;

    {
        logger = LoggerFactory.getLogger(IndexController.class);
    }

    private IUserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if(session.getAttribute("userName") != null && !session.getAttribute("userName").toString().isEmpty()) {
            // add `message` attribute
            model.addAttribute("message", "Current user: " + session.getAttribute("userName").toString());
        } else {
            model.addAttribute("message", "No user selected");
        }
        List<User> users =  userService.findAll();
        model.addAttribute("users", users);
        // return view name
        return "index";
    }

    @PostMapping("/setCurrentUser")
    public String setCurrentUser(HttpSession session, @RequestParam("userId") String id, RedirectAttributes attributes) {
        User selectedUser = userService.findById(Integer.parseInt(id)).orElse(null);
        if(selectedUser != null) {
            session.setAttribute("roleId", selectedUser.getRole().getId());
            session.setAttribute("userName", selectedUser.getName());
        } else {
            session.setAttribute("roleId", "");
            session.setAttribute("userName", "");
        }
        return "redirect:/";
    }
}
