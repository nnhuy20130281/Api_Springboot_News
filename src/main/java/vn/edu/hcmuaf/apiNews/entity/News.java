package vn.edu.hcmuaf.apiNews.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "news")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;
    private String image;
    private String content;
    private boolean isDelete;
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToMany(mappedBy = "listNews", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Category> categories;
    // getters and setters
}

