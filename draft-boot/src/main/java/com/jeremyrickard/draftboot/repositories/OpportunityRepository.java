package com.jeremyrickard.draftboot.repositories;

import com.jeremyrickard.draftboot.models.Opportunity;

import org.springframework.data.repository.CrudRepository;

public interface OpportunityRepository extends CrudRepository<Opportunity, String> {

}