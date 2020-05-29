package com.faq.faq.Usecases;


import com.faq.faq.models.FAQCategoryEntity;
import com.faq.faq.models.FAQEntity;
import com.faq.faq.persistence.repository.FAQCategoryEntityRepository;
import com.faq.faq.persistence.repository.FAQEntityRepository;
import com.faq.faq.persistence.response.FAQQAResponse;
import com.faq.faq.persistence.response.FAQResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import io.micrometer.core.instrument.util.IOUtils;

import javax.inject.Named;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Named
public class PopulateFaqUseCase {

    private FAQCategoryEntityRepository faqCategoryEntityRepository;
    private FAQEntityRepository faqEntityRepository;
    private Gson gson;

    @Autowired
    public PopulateFaqUseCase(FAQCategoryEntityRepository faqCategoryEntityRepository, FAQEntityRepository faqEntityRepository, Gson gson) {
        this.faqCategoryEntityRepository = faqCategoryEntityRepository;
        this.faqEntityRepository = faqEntityRepository;
        this.gson = gson;
    }

    private FAQQAResponse  fromEntityToResponse(FAQEntity entity ) {
        return FAQQAResponse .builder().id(entity.getId())
                .question(entity.getQuestion())
                .answer(entity.getAnswer()).build();
    }

    private FAQResponse  fromEntityToResponse(FAQCategoryEntity entity ) {
        return FAQResponse.builder()
                .code(entity.getCode())
                .category(entity.getCategory())
                .build();
    }

    public List<FAQResponse> getAllFAQs(){
        List<FAQCategoryEntity> projectCategories = faqCategoryEntityRepository.findAll();
        if(projectCategories.isEmpty()){
            createDefaultFaq();
        }
        List<FAQResponse> responses = new ArrayList<>();
        for(FAQCategoryEntity faqCategoryEntity : projectCategories){
            FAQResponse faqResponse = fromEntityToResponse(faqCategoryEntity);
            List<FAQQAResponse> faqEntities = faqEntityRepository.findByCategory(faqCategoryEntity).stream().map(this::fromEntityToResponse).collect(Collectors.toList());
            faqResponse.setContent(faqEntities);
            responses.add(faqResponse);
        }
        return responses;
    }

    public FAQResponse getFAQByCategory(String code){
        FAQCategoryEntity faqCategoryEntity = faqCategoryEntityRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Not found. FAQ category with code: " + code));
        FAQResponse faqResponse = fromEntityToResponse(faqCategoryEntity);
        List<FAQQAResponse> faqEntities = faqEntityRepository.findByCategory(faqCategoryEntity).stream().map(this::fromEntityToResponse).collect(Collectors.toList());
        faqResponse.setContent(faqEntities);
        return faqResponse;
    }

    public List<FAQEntity> getEntityByCategory(){
        return faqEntityRepository.findAll();
    }

    public void createDefaultFaq() {
        if(faqCategoryEntityRepository.count() != 0) {
            return;
        }
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/faq.json");
        try {
            String fileContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if(!StringUtils.isEmpty(fileContent)) {
                Type collectionType = new TypeToken<List<CategoryData>>(){}.getType();
                List<CategoryData> responseList = gson.fromJson(fileContent, collectionType);
                System.out.println("total tier levels: " + responseList.size());
                System.out.println(responseList.toString());
                responseList.forEach(tierLevelData -> {
                    FAQCategoryEntity levelEntity = FAQCategoryEntity.builder()
                            .code(tierLevelData.getCode())
                            .category(tierLevelData.getCategory())
                            .build();
                    tierLevelData.getContent().forEach(content -> {
                        FAQEntity questions = FAQEntity.builder().answer(content.getAnswer())
                                .question(content.getQuestion())
                                .category(levelEntity)
                                .build();

                        faqCategoryEntityRepository.save(levelEntity);
                        faqEntityRepository.save(questions);
                    });
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("Unable to create tier levels");
        }
    }

    @Data
    private static class CategoryData {
        private String code;
        @SerializedName("category")
        private String category;
        @SerializedName("content")
        private List<AnswersData> content;
    }

    @Data
    private static class AnswersData {
        @SerializedName("question")
        private String question;
        @SerializedName("answer")
        private String answer;
    }
}
