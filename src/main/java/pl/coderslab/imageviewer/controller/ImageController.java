package pl.coderslab.imageviewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.imageviewer.model.Image;
import pl.coderslab.imageviewer.service.IImageService;
import pl.coderslab.imageviewer.service.ImageService;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageController {
    private IImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public String findImage(HttpSession session, Model model){
        if(session.getAttribute("userName") != null && !session.getAttribute("userName").toString().isEmpty()) {
            // add `message` attribute
            model.addAttribute("message", "Current user: " + session.getAttribute("userName").toString());
        } else {
            model.addAttribute("message", "No user selected");
        }

        List<Image> images = new ArrayList<>();
        if(session.getAttribute("roleId") != null && !session.getAttribute("roleId").toString().isEmpty()){
            int userRoleId = (Integer) session.getAttribute("roleId");
            images = (List<Image>)imageService.findAll().stream().filter(image -> image.getRole().getId() == userRoleId).collect(Collectors.toList());
        }

        model.addAttribute("images", images);

        return "images";
    }

    @PostMapping("images/deleteImage")
    public String deleteUser(@RequestParam String id){
        imageService.deleteById(Integer.parseInt(id));
        return "redirect:/images";
    }
}