package pl.coderslab.imageviewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.imageviewer.model.User;
import pl.coderslab.imageviewer.service.*;

@Controller
public class RegisterUserController {
    private IUserService userService;
    private IRoleService roleService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/registeruser")
    public String homepage() {
        return "registeruser";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("roleId") Integer roleId, RedirectAttributes attributes) {

        User user = new User(name, password);
        user.setRole(roleService.findById(roleId).orElse(null));
        userService.save(user);

        attributes.addFlashAttribute("message", "You successfully registered user " + name + '!');

        return "redirect:/registeruser";
    }

}
