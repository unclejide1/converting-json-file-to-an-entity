package com.faq.faq.persistence.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQQAResponse {
    private Long id;
    private String question;
    private String answer;
}
