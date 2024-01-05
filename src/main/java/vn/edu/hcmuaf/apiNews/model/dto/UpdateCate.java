package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCate {
    private String name;
    private boolean isDelete;
    private String createdBy;
    private Date createdDate;
}
