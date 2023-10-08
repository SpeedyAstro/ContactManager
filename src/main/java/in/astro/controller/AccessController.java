package in.astro.controller;

import in.astro.dao.IUserRepository;
import in.astro.model.User;
import in.astro.service.EmailService;
import in.astro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class AccessController {
    Random random = new Random(1000);

    @Autowired
    private IUserService service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping("/forgot")
    public String openEmailForm(){
        return "forgot_email_form";
    }

    @RequestMapping("/send-otp")
    public String sendOTP(@RequestParam String email, HttpSession session){
        int otp = random.nextInt(9999);
        String subject = "OTP From Smart Contact Manager";
        String message = "" +
                "<div style='border:1px solid #e2e2e2; padding:20px'>" +
                "<h1>" +
                "OTP IS :: " +
                "<b>" + otp+
                "</b>" +
                "</h1>" +
                "</div>";
        String to  = email;
        boolean flag = this.emailService.sendEmail(subject, message, to);
        if (flag){
            session.setAttribute("my_otp",otp);
            session.setAttribute("email",email);
        }
        return "verify_otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam int otp, HttpSession session){
        int my_otp = (Integer) session.getAttribute("my_otp");
        String email = (String) session.getAttribute("email");
        if (my_otp==otp){
            User user = this.service.getUserByEmail(email);
            if (user==null){
                return "forgot_email_form";
            }
            return "password_change_form";
        }
        return "verify_otp";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String new_password, HttpSession session){
        String email = (String) session.getAttribute("email");
        User user = service.getUserByEmail(email);
        user.setPassword(encoder.encode(new_password));
        service.updateUser(user);
        return "redirect:/signin";
    }

}
