package vn.edu.hcmuaf.apiNews.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "isDelete")
    private Boolean isDelete;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cate_news",
            joinColumns = @JoinColumn(name = "id_cate"),
            inverseJoinColumns = @JoinColumn(name = "id_news")
    )
    @JsonManagedReference
    private Set<News> listNews;
}
