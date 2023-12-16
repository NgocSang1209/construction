package com.project.webxaydung.Services;
import com.project.webxaydung.Dto.ContactDTO;
import com.project.webxaydung.Models.Contact;
import com.project.webxaydung.Repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    public Contact insertContact(ContactDTO contactDTO){
        if (!isValidEmail(contactDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email không hợp lệ.");
        }
        if (!isValidPhoneNumber(contactDTO.getPhone())) {
            throw new DataIntegrityViolationException("Số điện thoại không hợp lệ.");
        }
        Contact newContact= Contact
                .builder()
                .name(contactDTO.getName())
                .address(contactDTO.getAddress())
                .phone(contactDTO.getPhone())
                .email(contactDTO.getEmail())
                .message(contactDTO.getMessage())
                .build();
        return contactRepository.save(newContact);
    }
    public List<Contact> getAllContact(){
        return contactRepository.findAll();
    }
    public Contact getContactById(int id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("contact not found"));
    }

    public List<Contact> getContactsByDate(Date date) {
        // Chuyển đổi Date thành LocalDateTime
        LocalDateTime endOfDay = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime startOfDay= endOfDay.minusDays(1); // giảm 1 ngày

        return contactRepository.findBySubDateBetween(startOfDay, endOfDay);
    }
    public void deleteContact(int  id) {

        contactRepository.deleteById(id);
    }

    //xác thực email
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Kiểm tra xem email có ít nhất một ký tự @ và ít nhất một dấu chấm sau ký tự @
        return email.matches(".*@.*\\..*");
    }
    // xác thực phone
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "\\d{10}"; // Ví dụ: 10 chữ số
        return phoneNumber.matches(regex);
    }
}
