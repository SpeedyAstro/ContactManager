package in.astro.controller;

import in.astro.model.Contact;
import in.astro.model.User;
import in.astro.service.IContactService;
import in.astro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String processContact(@ModelAttribute Contact contact, Principal principal){
        String name = principal.getName();
        System.out.println(contact);
        User user = service.getUserByEmail(name);
        contact.setUser(user);
        user.getContacts().add(contact);
        service.saveUser(user);
        return "normal/add_contact_form";
    }
}
