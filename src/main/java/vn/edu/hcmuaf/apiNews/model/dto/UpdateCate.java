package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCate {
    private String name;
    private boolean delete;
    private String createdBy;
    private Date createdDate;
}
