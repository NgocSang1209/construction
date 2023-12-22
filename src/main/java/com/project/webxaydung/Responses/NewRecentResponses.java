package com.project.webxaydung.Responses;

import com.project.webxaydung.Models.New;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewRecentResponses {
    private  int id ;
    private String title;
    private String short_description;
    private String thumbnail;
    private LocalDateTime date_published;
    public static NewRecentResponses fromNew(New news ) {
        NewRecentResponses newRecentResponses= NewRecentResponses.builder()
                .id(news.getId())
                .title(news.getTitle())
                .short_description(news.getShort_description())
                .thumbnail(news.getThumbnail())
                .build();
        newRecentResponses.setDate_published(news.getDatePublished());
        return newRecentResponses;
    }
}
