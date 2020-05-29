package com.faq.faq.models.dao;

import com.faq.faq.models.FAQCategoryEntity;

import java.util.List;

public interface FAQCategoryEntityDao extends CrudDao<FAQCategoryEntity, Long> {
    List<FAQCategoryEntity> getFAQs();
}
