package in.astro.controller;

import in.astro.model.Contact;
import in.astro.model.User;
import in.astro.service.IContactService;
import in.astro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IContactService contactService;


    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable String query, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Contact> contacts = contactService.searchByName(query, user);
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }
}
