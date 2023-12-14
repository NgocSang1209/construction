package com.project.webxaydung.Services;
import com.project.webxaydung.Dto.CategoryDTO;
import com.project.webxaydung.Models.Category;
import com.project.webxaydung.Repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService  {
    private final CategoryRepository categoryRepository;
    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .code(categoryDTO.getCode())
                .build();
        return categoryRepository.save(newCategory);
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Transactional
    public Category updateCategory(int categoryId,
                                   CategoryDTO categoryDTO) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Không tìm thấy category với ID: " + categoryId);
        }
        Category existingCategory = getCategoryById(categoryId);
        if(categoryDTO.getName()!= null) {
            existingCategory.setName(categoryDTO.getName());
        }
        if(categoryDTO.getCode() != null) {
            existingCategory.setCode(categoryDTO.getCode());
        }
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Transactional
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
