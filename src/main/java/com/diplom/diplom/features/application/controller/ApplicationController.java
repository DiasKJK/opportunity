package com.diplom.diplom.features.application.controller;

import com.diplom.diplom.features.application.dto.ApplicationDto;
import com.diplom.diplom.features.application.dto.ApplicationStatusUpdateDto;
import com.diplom.diplom.features.application.model.Application;
import com.diplom.diplom.features.application.model.ApplicationStatus;
import com.diplom.diplom.features.application.service.ApplicationService;
import com.diplom.diplom.features.authentication.model.AuthenticationUser;
import com.diplom.diplom.features.authentication.model.Role;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(
            @Valid @RequestBody ApplicationDto applicationDto,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        Application application = applicationService.createApplication(applicationDto, user.getId());
        return ResponseEntity.ok(application);
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<Application>> getMyApplications(
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        List<Application> applications = applicationService.getUserApplications(user.getId());
        return ResponseEntity.ok(applications);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications(
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can view all applications");
        }
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(
            @PathVariable ApplicationStatus status,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can filter applications by status");
        }
        List<Application> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplication(
            @PathVariable Long applicationId,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        Application application = applicationService.getApplication(applicationId);

        // Users can only view their own applications, admins can view all
        if (user.getRole() != Role.ADMIN && !application.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You can only view your own applications");
        }

        return ResponseEntity.ok(application);
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusUpdateDto statusUpdateDto,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        Application application = applicationService.updateApplicationStatus(applicationId, statusUpdateDto, user.getId());
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{applicationId}/withdraw")
    public ResponseEntity<String> withdrawApplication(
            @PathVariable Long applicationId,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        applicationService.withdrawApplication(applicationId, user.getId());
        return ResponseEntity.ok("Application withdrawn successfully");
    }

    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<List<Application>> getApplicationsForOpportunity(
            @PathVariable Long opportunityId,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can view applications for specific opportunities");
        }
        List<Application> applications = applicationService.getApplicationsForOpportunity(opportunityId);
        return ResponseEntity.ok(applications);
    }
}