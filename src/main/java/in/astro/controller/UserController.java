package in.astro.controller;

import in.astro.helper.Message;
import in.astro.model.Contact;
import in.astro.model.User;
import in.astro.service.IContactService;
import in.astro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;
    @Autowired
    private IContactService contactService;

    @ModelAttribute
    public void loggedInUser(Model model, Principal principal) {
        String name = principal.getName();
        User user = service.getUserByEmail(name);
        model.addAttribute("user", user);
    }

    //    DashBoard Home
    @GetMapping("/index")
    public String dashboard(Model model, Principal principal) {
        return "normal/dashboard";
    }

    //    Contact Form
    @GetMapping("/add-contact")
    public String openContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    //    @PostMapping("/process-contact")
//    public String processContact(@ModelAttribute Contact contact,
//                                 @RequestParam(value = "profileImage", required = false) MultipartFile file, Principal principal,
//                                 HttpSession session) throws IOException {
//        try {
//            String name = principal.getName();
//            User user = service.getUserByEmail(name);
//            if (file.isEmpty()) {
//                System.out.println("file is empty");
//            } else {
//                contact.setImage(file.getOriginalFilename());
//                File file1 = new ClassPathResource("static/img").getFile();
//                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
//                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            }
//            user.getContacts().add(contact);
//            contact.setUser(user);
//            service.updateUser(user);
//            session.setAttribute("message",new Message("Your contact has been saved successfully","Success"));
//        }catch (Exception e){
//            session.setAttribute("message",new Message("contact not saved ","danger"));
//            e.getMessage();
//        }
//        return "normal/add_contact_form";
//    }
//    @PostMapping("/process-contact")
//    public String processContact(@ModelAttribute Contact contact,
//                                 @RequestParam(value = "profileImage", required = false) MultipartFile file,
//                                 Principal principal, HttpSession session) throws IOException {
//        try {
//            String name = principal.getName();
//            User user = service.getUserByEmail(name);
//
//            if (!file.isEmpty()) {
//                // Validate the uploaded file as an image here if needed.
//
//                contact.setImage(file.getOriginalFilename());
//
//                // Ensure that the image directory exists and is writable.
//                File imageDirectory = new File("static/img");
//                if (!imageDirectory.exists()) {
//                    imageDirectory.mkdirs();
//                }
//
//                Path path = Paths.get(imageDirectory.getAbsolutePath() + File.separator + file.getOriginalFilename());
//                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            user.getContacts().add(contact);
//            contact.setUser(user);
//            service.updateUser(user);
//            session.setAttribute("message", new Message("Your contact has been saved successfully", "success"));
//        } catch (Exception e) {
//            // Log the exception for debugging purposes.
//            e.printStackTrace();
//
//            session.setAttribute("message", new Message("Contact not saved", "danger"));
//        }finally {
//
//        }
//        return "normal/add_contact_form";
//    }
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam(value = "old_img", required = false) String old_img,
                                 @RequestParam(value = "profileImage", required = false) MultipartFile file,
                                 Principal principal, HttpSession session) {
        String name = principal.getName();
        User user = service.getUserByEmail(name);

        try {
            if (!file.isEmpty()) {
                // Validate the uploaded file as an image here if needed.
                contact.setImage(file.getOriginalFilename());
                File deleteFile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deleteFile, old_img);
                file1.delete();

                // Ensure that the image directory exists and is writable.
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                contact.setImage(file.getOriginalFilename());
            } else if ((!old_img.isEmpty()) || (old_img != null)) {
                contact.setImage(old_img);
            } else {
                contact.setImage("contact.jpg");
            }
//            System.out.println("*********************************************************"+user);
            System.out.println("*********************************" + contact.getName() + "*********");
            user.getContacts().add(contact);
            contact.setUser(user);
            service.updateUser(user);
            session.setAttribute("message", new Message("Your contact has been saved successfully", "success"));
        } catch (Exception e) {
            // Log the exception for debugging purposes.
            e.printStackTrace();
            session.setAttribute("message", new Message("Contact not saved", "danger"));
        }

//        return "redirect:/user/"+contact.getCid()+"/contact";
        return "redirect:/user/contacts/0";
    }

    // Show contacts
    @GetMapping("/contacts/{page}")
    public String showContacts(@PathVariable Integer page, Model model, Principal principal) {
        model.addAttribute("title", "Show Contacts");
        String name = principal.getName();
        User user = service.getUserByEmail(name);
        Page<Contact> contacts = contactService.findContactUserId(user.getId(), page);
        model.addAttribute("contacts", contacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", contacts.getTotalPages());
        return "normal/show_contact";
    }

    //    show contact detail
    @GetMapping("/{cid}/contact")
    public String showContact(@PathVariable Integer cid, Model model, Principal principal) {
        String name = principal.getName();
        User user = service.getUserByEmail(name);
//        Optional<Contact> optional = contactService.findById(cid);
//        if(optional.isEmpty()){
//
//        }
        model.addAttribute("id", cid);
        Contact contact = contactService.getContactDetails(cid, user);
//        model.addAttribute("title",contact.getName());
        model.addAttribute("contact", contact);
        return "normal/contact_detail";
    }

    @GetMapping("/{cid}/delete")
    public String deleteContact(@PathVariable Integer cid, HttpSession session) {
        contactService.deleteContact(cid);
        session.setAttribute("message", new Message("Contact Deleted Successfully", "success"));
        return "redirect:/user/contacts/0";
    }

    @GetMapping("/{cid}/update")
    public String Updateform(@PathVariable Integer cid,
                             Map<String, Object> map, HttpSession session) {
        Optional<Contact> optional = contactService.findById(cid);
        Contact contact = optional.get();
        if (optional.isPresent()) {
            map.put("contact", optional.get());
        }
//        System.out.println(optional.get());
        return "normal/add_contact_form";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "Profile Page");
        return "normal/profile";
    }

}
