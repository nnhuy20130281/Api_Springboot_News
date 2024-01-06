package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

import java.util.List;
@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNews {
    private String title;
    private String description;
    private String image;
    private String content;
    private String createdBy;
    private List<Long> idCategories;

}
