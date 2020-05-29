package com.faq.faq.persistence.repository;

import com.faq.faq.models.FAQCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FAQCategoryEntityRepository extends JpaRepository<FAQCategoryEntity, Long> {
    Optional<FAQCategoryEntity> findByCode(String code);
    long count();
}
