package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CateDto {
    private long id;
    private String name;
    private boolean isDelete;

}
