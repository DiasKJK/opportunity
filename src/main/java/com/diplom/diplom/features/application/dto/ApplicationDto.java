package com.diplom.diplom.features.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApplicationDto {

    @NotNull(message = "Opportunity ID is required")
    private Long opportunityId;

    @NotBlank(message = "Cover letter is required")
    private String coverLetter;

    public ApplicationDto() {}

    public ApplicationDto(Long opportunityId, String coverLetter) {
        this.opportunityId = opportunityId;
        this.coverLetter = coverLetter;
    }

    public Long getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Long opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
}