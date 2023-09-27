package in.astro.controller;

import in.astro.helper.Message;
import in.astro.model.User;
import in.astro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private IUserService service;
    @GetMapping("/")
    public String test(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "Home";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signUp")
    public String signUp(Model model){
        model.addAttribute("title","SignUp - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signUp";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,Model model, HttpSession session){
        String msg = service.saveUser(user);
        model.addAttribute("user",new User());
        session.setAttribute("message",new Message(msg,"alert-success"));
        return "signUp";
    }

    @GetMapping("/signin")
    public String loginPage(Model model){
        model.addAttribute("title","Login - Smart Contact Manager");
        return "login";
    }
}
