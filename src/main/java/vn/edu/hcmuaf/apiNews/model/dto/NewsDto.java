package vn.edu.hcmuaf.apiNews.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private String title;
    private String description;
    private String image;
    private String content;
    private String createdBy;
    private List<Long> idCategories;

}
