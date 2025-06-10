package com.diplom.diplom.features.opportunity;


import com.diplom.diplom.features.authentication.model.AuthenticationUser;
import com.diplom.diplom.features.authentication.model.Role;
import com.diplom.diplom.features.opportunity.Opportunity;
import com.diplom.diplom.features.opportunity.OpportunityCategory;
import com.diplom.diplom.features.opportunity.OpportunityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@CrossOrigin(origins = "*")
public class OpportunityController {

    private final OpportunityRepository opportunityRepository;

    public OpportunityController(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    @GetMapping
    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    @GetMapping("/category/{category}")
    public List<Opportunity> getByCategory(@PathVariable OpportunityCategory category) {
        return opportunityRepository.findByCategory(category);
    }

    @PostMapping
    public Opportunity createOpportunity(@RequestBody Opportunity opportunity
    //@RequestAttribute("authenticatedUser") AuthenticationUser user
    ) {

//        if (user.getRole() != Role.ADMIN) {
//            throw new IllegalAccessError("Only admins can create opportunities.");
//        }
        return opportunityRepository.save(opportunity);
    }

    @PatchMapping
    public Opportunity editOpportunity(@RequestBody Opportunity opportunity
                                       //@RequestAttribute("authenticatedUser") AuthenticationUser user
    ) {
//        if (user.getRole() != Role.ADMIN) {
//            throw new IllegalAccessError("Only admins can edit opportunities.");
//        }
        return opportunityRepository.save(opportunity);
    }
}
