package com.project.webxaydung.Controllers;
import com.project.webxaydung.Models.Candidate;
import com.project.webxaydung.Models.New;
import com.project.webxaydung.Dto.NewDTO;
import com.project.webxaydung.Repositories.CategoryRepository;
import com.project.webxaydung.Responses.NewListResponse;
import com.project.webxaydung.Responses.NewRecentResponses;
import com.project.webxaydung.Responses.NewResponses;
import com.project.webxaydung.Services.NewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/new")
@RequiredArgsConstructor
@Validated
public class NewController {
    private  final NewService newService;
    private final CategoryRepository categoryRepository;

    @PostMapping("")
    //POST http://localhost:8088/v1/api/new
    public ResponseEntity<?> createNew(
            @Valid @RequestBody NewDTO newDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            New newNew = newService.createNews(newDTO);
            return ResponseEntity.ok(newNew);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
            @PathVariable("id") int newId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            New existingNew = newService.getNewById(newId);

            // Kiểm tra xem 'file' có giá trị là null không.
            if (file == null || file.isEmpty()) {
                return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra kích thước file và định dạng
            if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                return new ResponseEntity<>("file load to large", HttpStatus.BAD_REQUEST);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return new ResponseEntity<>("Upload image file must be image ", HttpStatus.BAD_REQUEST);
            }

            // Lưu file và cập nhật thumbnail trong DTO
            String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file

            // Lưu vào đối tượng trong DB
            NewDTO newDTO= new NewDTO();
            newDTO.setThumbnail(filename);
            newService.updateNew(newId,newDTO);

            return ResponseEntity.ok().body("add image success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImageContent(
            @RequestParam("file") MultipartFile file
    ) {
        try {

            // Kiểm tra xem 'file' có giá trị là null không.
            if (file == null || file.isEmpty()) {
                return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra kích thước file và định dạng
            if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                return new ResponseEntity<>("file load to large", HttpStatus.BAD_REQUEST);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return new ResponseEntity<>("Upload image file must be image ", HttpStatus.BAD_REQUEST);
            }

            // Lưu file và cập nhật thumbnail trong DTO
            String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file

            return ResponseEntity.ok().body(filename);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    @GetMapping("")
    public ResponseEntity<NewListResponse> getNews(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").ascending()
        );
        Page<NewResponses> newPage = newService.getAllNews(keyword, categoryId, pageRequest);
        int totalPages = newPage.getTotalPages();
        List<NewResponses> news = newPage.getContent();
        return ResponseEntity.ok(NewListResponse
                .builder()
                .news(news)
                .totalPages(totalPages)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewById(
            @PathVariable("id") int newId
    ) {
        try {
            New existingNew = newService.getNewById(newId);
            return ResponseEntity.ok(NewResponses.fromNew(existingNew));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentNews() {
        List<NewRecentResponses> newRecentResponses=newService.getRecentNews()
                .stream()
                .map(NewRecentResponses::fromNew).toList();
        return ResponseEntity.ok(newRecentResponses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNew(
            @PathVariable int id,
            @RequestBody NewDTO newDTO) {
        try {
            New updatedNew = newService.updateNew(id, newDTO);
            return ResponseEntity.ok(updatedNew);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletesNew(@PathVariable int id) {
        try {
            newService.deleteNew(id);
            return ResponseEntity.ok(String.format("New with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
