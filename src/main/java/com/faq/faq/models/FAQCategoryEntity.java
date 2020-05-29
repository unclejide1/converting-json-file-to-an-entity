package com.faq.faq.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faq_categories")
public class FAQCategoryEntity extends AbstractBaseEntity<Long> {


    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String code;

    //one to many relationship between a category with question and answer entity
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH,  orphanRemoval = true)
    private List<FAQEntity> content = new ArrayList<>();


}
