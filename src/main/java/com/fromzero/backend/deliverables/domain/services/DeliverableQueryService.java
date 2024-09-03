package com.fromzero.backend.deliverables.domain.services;

import com.fromzero.backend.deliverables.domain.model.aggregates.Deliverable;
import com.fromzero.backend.deliverables.domain.model.queries.GetAllDeliverablesByProjectIdQuery;
import com.fromzero.backend.deliverables.domain.model.queries.GetCompletedDeliverablesQuery;
import com.fromzero.backend.deliverables.domain.model.queries.GetDeliverableByIdQuery;

import java.util.List;
import java.util.Optional;

public interface DeliverableQueryService {
    List<Deliverable> handle(GetAllDeliverablesByProjectIdQuery query);
    Optional<Deliverable> handle(GetDeliverableByIdQuery query);
    Long handle(GetCompletedDeliverablesQuery query);
}
