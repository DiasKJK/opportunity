package com.diplom.diplom.features.application.service;

import com.diplom.diplom.features.application.dto.ApplicationDto;
import com.diplom.diplom.features.application.dto.ApplicationStatusUpdateDto;
import com.diplom.diplom.features.application.model.Application;
import com.diplom.diplom.features.application.model.ApplicationStatus;
import com.diplom.diplom.features.application.repository.ApplicationRepository;
import com.diplom.diplom.features.authentication.model.AuthenticationUser;
import com.diplom.diplom.features.authentication.model.Role;
import com.diplom.diplom.features.authentication.repository.AuthenticationUserRepository;
import com.diplom.diplom.features.opportunity.Opportunity;
import com.diplom.diplom.features.opportunity.OpportunityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AuthenticationUserRepository userRepository;
    private final OpportunityRepository opportunityRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              AuthenticationUserRepository userRepository,
                              OpportunityRepository opportunityRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.opportunityRepository = opportunityRepository;
    }

    public Application createApplication(ApplicationDto applicationDto, Long userId) {
        AuthenticationUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Opportunity opportunity = opportunityRepository.findById(applicationDto.getOpportunityId())
                .orElseThrow(() -> new IllegalArgumentException("Opportunity not found"));

        // Check if user already applied to this opportunity
        Optional<Application> existingApplication = applicationRepository
                .findByUserIdAndOpportunityId(userId, applicationDto.getOpportunityId());

        if (existingApplication.isPresent()) {
            throw new IllegalArgumentException("You have already applied to this opportunity");
        }

        Application application = new Application(user, opportunity, applicationDto.getCoverLetter());
        return applicationRepository.save(application);
    }

    public List<Application> getUserApplications(Long userId) {
        return applicationRepository.findByUserIdOrderByApplicationDateDesc(userId);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAllByOrderByApplicationDateDesc();
    }

    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatusOrderByApplicationDateDesc(status);
    }

    public Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
    }

    public Application updateApplicationStatus(Long applicationId, ApplicationStatusUpdateDto statusUpdateDto, Long adminId) {
        AuthenticationUser admin = userRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can update application status");
        }

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        application.setStatus(statusUpdateDto.getStatus());
        application.setAdminNotes(statusUpdateDto.getAdminNotes());

        return applicationRepository.save(application);
    }

    public void withdrawApplication(Long applicationId, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        if (!application.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You can only withdraw your own applications");
        }

        if (application.getStatus() == ApplicationStatus.ACCEPTED) {
            throw new IllegalArgumentException("Cannot withdraw an accepted application");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        applicationRepository.save(application);
    }

    public List<Application> getApplicationsForOpportunity(Long opportunityId) {
        return applicationRepository.findByOpportunityIdOrderByApplicationDateDesc(opportunityId);
    }
}