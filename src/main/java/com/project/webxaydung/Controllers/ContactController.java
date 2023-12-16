package com.project.webxaydung.Controllers;
import com.project.webxaydung.Dto.ContactDTO;
import com.project.webxaydung.Models.Contact;
import com.project.webxaydung.Services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/contact")
@RequiredArgsConstructor
@Validated
public class ContactController {
private  final ContactService contactService;

    @PostMapping("")
    public ResponseEntity<?> insertContact(@RequestBody ContactDTO contactDTO, BindingResult result){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try{
            contactService.insertContact(contactDTO);
            return ResponseEntity.ok("Insert contact Successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")// http://localhost:8080/api/v1/contact
    public ResponseEntity<List<Contact>> getAllEmail(){
        List<Contact> contacts=contactService.getAllContact();
        return  ResponseEntity.ok(contacts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(
            @PathVariable("id") int id
    ) {
        contactService.getContactById(id);
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @GetMapping("/byDate")
    public List<Contact> getContactsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return contactService.getContactsByDate(date);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok("Delete category with id: "+id+" successfully");
    }
}
