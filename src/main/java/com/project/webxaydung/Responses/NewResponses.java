package com.project.webxaydung.Responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.webxaydung.Models.New;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewResponses {
    private  int id ;
    private  int category_id;
    private String title;
    private String short_description;
    private String content;
    private String thumbnail;
    private LocalDateTime date_published;
    public static NewResponses fromNew(New news ) {
        NewResponses newResponse = NewResponses.builder()
                .id(news.getId())
                .title(news.getTitle())
                .short_description(news.getShort_description())
                .thumbnail(news.getThumbnail())
                .content(news.getContent())
                .build();
        newResponse.setDate_published(news.getDate_published());
        return newResponse;
    }
}
