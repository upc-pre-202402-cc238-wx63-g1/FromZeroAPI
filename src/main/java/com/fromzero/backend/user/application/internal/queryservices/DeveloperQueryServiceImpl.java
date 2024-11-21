package com.fromzero.backend.user.application.internal.queryservices;

import com.fromzero.backend.user.domain.model.aggregates.Developer;
import com.fromzero.backend.user.domain.model.queries.GetAllDevelopersAsyncQuery;
import com.fromzero.backend.user.domain.model.queries.GetDeveloperByIdQuery;
import com.fromzero.backend.user.domain.model.queries.GetDeveloperByUserIdAsyncQuery;
import com.fromzero.backend.user.domain.services.DeveloperQueryService;
import com.fromzero.backend.user.infrastructure.persistence.jpa.repositories.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperQueryServiceImpl implements DeveloperQueryService{

    private final DeveloperRepository developerRepository;

    public DeveloperQueryServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }


    @Override
    public List<Developer> handle(GetAllDevelopersAsyncQuery query) {
        return developerRepository.findAll();
    }

    @Override
    public Optional<Developer> handle(GetDeveloperByUserIdAsyncQuery query) {
        return developerRepository.findDeveloperByUser_Id(query.userId());
    }

    @Override
    public Optional<Developer> handle(GetDeveloperByIdQuery query) {
        return this.developerRepository.findById(query.developerId());
    }
}
