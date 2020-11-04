package pl.coderslab.imageviewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.imageviewer.model.Image;
import pl.coderslab.imageviewer.service.IImageService;
import pl.coderslab.imageviewer.service.IRoleService;
import pl.coderslab.imageviewer.service.ImageService;
import pl.coderslab.imageviewer.service.RoleService;

import java.io.IOException;

@Controller
public class UploadImageController {
    private IImageService imageService;
    private IRoleService roleService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/imageupload")
    public String homepage() {
        return "imageupload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("roleId") Integer roleId, RedirectAttributes attributes) {

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select an image to upload.");
            return "redirect:/imageupload";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Image image = new Image(file.getInputStream().readAllBytes(), title, description);
            image.setRole(roleService.findById(roleId).orElse(null));
            imageService.save(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/images";
    }

}
