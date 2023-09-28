package in.astro.controller;

import in.astro.model.Contact;
import in.astro.model.User;
import in.astro.service.IContactService;
import in.astro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;
    @Autowired
    private IContactService contactService;

    @ModelAttribute
    public void loggedInUser(Model model,Principal principal){
        String name = principal.getName();
        User user = service.getUserByEmail(name);
        model.addAttribute("user",user);
    }
//    DashBoard Home
    @GetMapping("/index")
    public String dashboard(Model model, Principal principal){
        return "normal/dashboard";
    }
//    Contact Form
    @GetMapping("/add-contact")
    public String openContactForm(Model model){
        model.addAttribute("title","Add Contact");
        model.addAttribute("contact",new Contact());
        return "normal/add_contact_form";
    }
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam("profileImage") MultipartFile file, Principal principal) throws IOException {
        try {
            String name = principal.getName();
            User user = service.getUserByEmail(name);
            if (file.isEmpty()) {
            } else {
                contact.setImage(file.getOriginalFilename());
                File file1 = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            user.getContacts().add(contact);
            contact.setUser(user);
            service.saveUser(user);
        }catch (Exception e){
            e.getMessage();
        }
        return "normal/add_contact_form";
    }
}
