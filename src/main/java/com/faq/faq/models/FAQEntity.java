package com.faq.faq.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faq_question_and_answers")
public class FAQEntity extends AbstractBaseEntity<Long> {

    @Column(nullable = false)
    private String question;


    @Column(columnDefinition = "TEXT",nullable = false)
    private String answer;

    //many to one relationship between the question and answer entity to the category entity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false)
    private FAQCategoryEntity category;

}
