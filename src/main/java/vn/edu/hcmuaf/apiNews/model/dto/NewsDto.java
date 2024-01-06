package vn.edu.hcmuaf.apiNews.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import vn.edu.hcmuaf.apiNews.entity.Category;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private String id;
    private String title;
    private String description;
    private String image;
    private String content;
    private boolean isDelete;
    private String createdBy;
    private Date createdDate;
    private Set<Category> categories;

}
