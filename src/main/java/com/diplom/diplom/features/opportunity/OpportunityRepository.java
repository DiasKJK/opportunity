package com.diplom.diplom.features.opportunity;



import com.diplom.diplom.features.opportunity.Opportunity;
import com.diplom.diplom.features.opportunity.OpportunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findByCategory(OpportunityCategory category);
}

