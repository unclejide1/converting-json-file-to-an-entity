package com.faq.faq.persistence.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQResponse {
        private String code;
        private String category;
        private List<FAQQAResponse> content;
}
