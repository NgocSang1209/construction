package com.project.webxaydung.Services;

import com.project.webxaydung.Dto.NewDTO;
import com.project.webxaydung.Models.Category;
import com.project.webxaydung.Models.New;
import com.project.webxaydung.Models.User;
import com.project.webxaydung.Repositories.CategoryRepository;
import com.project.webxaydung.Repositories.NewRepository;
import com.project.webxaydung.Responses.NewListRecentResponses;
import com.project.webxaydung.Responses.NewListResponse;
import com.project.webxaydung.Responses.NewRecentResponses;
import com.project.webxaydung.Responses.NewResponses;
import com.project.webxaydung.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewService  {
    private  final NewRepository newRepository;
    private final CategoryRepository categoryRepository;
    public New createNews(NewDTO newDTO)throws DataNotFoundException{
        Category existingCategory = categoryRepository
                .findById(newDTO.getCategory_id())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: "+newDTO.getCategory_id()));

        New newNews= New.builder()
                .category(existingCategory)
                .title(newDTO.getTitle())
                .short_description(newDTO.getShort_description())
                .content(newDTO.getContent())
                .thumbnail(newDTO.getThumbnail())
                .build();
        return newRepository.save(newNews);
    }
    public New getNewById(int newId) throws DataNotFoundException {
        Optional<New> optionalNew = newRepository.getDetailNewById(newId);
        if(optionalNew.isPresent()) {
            return optionalNew.get();
        }
        throw new DataNotFoundException("Cannot find New with id =" + newId);
    }
    public List<New> getRecentNews() {
        return newRepository.findTop5ByOrderByDatePublishedDesc();
    }
    public Page<NewResponses> getAllNews(String keyword,
                                         Long categoryId, PageRequest pageRequest) {
        // Lấy danh sách New theo trang (page), giới hạn (limit), và categoryId (nếu có)
        Page<New> newsPage;
        newsPage = newRepository.searchNews(categoryId, keyword, pageRequest);
        return newsPage.map(NewResponses::fromNew);
    }
    @Transactional
    public New updateNew(int id, NewDTO newDTO) throws Exception {

        New existingNew = getNewById(id);
        if(existingNew != null) {
            if(newDTO.getCategory_id()>0){
            Category existingCategory = categoryRepository
                    .findById(newDTO.getCategory_id())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: "+newDTO.getCategory_id()));
            existingNew.setCategory(existingCategory);
            }

            if (newDTO.getTitle() != null) {
                existingNew.setTitle(newDTO.getTitle());
            }
            if (newDTO.getShort_description() != null) {
                existingNew.setShort_description(newDTO.getShort_description());
            }
            if (newDTO.getContent() != null) {
                existingNew.setContent(newDTO.getContent());
            }
            if (newDTO.getThumbnail() != null) {
                existingNew.setThumbnail(newDTO.getThumbnail());
            }

            return newRepository.save(existingNew);
        }
        return null;
    }
    @Transactional
    public void deleteNew(int id) {
        Optional<New> optionalNew = newRepository.findById(id);
        optionalNew.ifPresent(newRepository::delete);
    }

}
