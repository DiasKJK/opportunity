package com.diplom.diplom.features.application.repository;

import com.diplom.diplom.features.application.model.Application;
import com.diplom.diplom.features.application.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserIdOrderByApplicationDateDesc(Long userId);
    List<Application> findAllByOrderByApplicationDateDesc();
    List<Application> findByStatusOrderByApplicationDateDesc(ApplicationStatus status);
    Optional<Application> findByUserIdAndOpportunityId(Long userId, Long opportunityId);
    List<Application> findByOpportunityIdOrderByApplicationDateDesc(Long opportunityId);
}