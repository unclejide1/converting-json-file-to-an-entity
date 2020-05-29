package com.faq.faq.persistence.repository;

import com.faq.faq.models.FAQCategoryEntity;
import com.faq.faq.models.FAQEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQEntityRepository extends JpaRepository<FAQEntity, Long> {
    List<FAQEntity> findByCategory(FAQCategoryEntity category);
    List<FAQEntity> findAll();
}
