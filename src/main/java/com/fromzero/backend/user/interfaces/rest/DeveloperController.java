package com.fromzero.backend.user.interfaces.rest;


import com.fromzero.backend.user.domain.model.queries.GetAllDevelopersAsyncQuery;
import com.fromzero.backend.user.domain.model.queries.GetDeveloperByIdQuery;
import com.fromzero.backend.user.domain.model.queries.GetDeveloperByUserIdAsyncQuery;
import com.fromzero.backend.user.domain.services.DeveloperCommandService;
import com.fromzero.backend.user.domain.services.DeveloperQueryService;
import com.fromzero.backend.user.interfaces.rest.resources.DeveloperResource;
import com.fromzero.backend.user.interfaces.rest.resources.UpdateDeveloperResource;
import com.fromzero.backend.user.interfaces.rest.transform.DeveloperResourceFromEntityAssembler;
import com.fromzero.backend.user.interfaces.rest.transform.UpdateDeveloperCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
@Tag(name = "Developers", description = "Developer Management Endpoints")
public class DeveloperController {

    private final DeveloperQueryService developerQueryService;
    private final DeveloperCommandService developerCommandService;

    public DeveloperController(DeveloperQueryService developerQueryService, DeveloperCommandService developerCommandService) {
        this.developerQueryService = developerQueryService;
        this.developerCommandService = developerCommandService;
    }

    @GetMapping
    public ResponseEntity<List<DeveloperResource>> getAllDevelopers() {
        var getAllDevelopersQuery = new GetAllDevelopersAsyncQuery();
        var developers = developerQueryService.handle(getAllDevelopersQuery);
        var developerResources = developers.stream().map(DeveloperResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(developerResources);
    }

    @GetMapping(value = "/{developerId}")
    public ResponseEntity<DeveloperResource> getDeveloperById(@PathVariable Long developerId) {
        var getDeveloperByIdQuery = new GetDeveloperByIdQuery(developerId);
        var developer = developerQueryService.handle(getDeveloperByIdQuery);
        if (developer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var developerResource = DeveloperResourceFromEntityAssembler.toResourceFromEntity(developer.get());
        return ResponseEntity.ok(developerResource);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<DeveloperResource> getDeveloperByUserId(@PathVariable Long userId) {
        var getDeveloperByUserIdAsyncQuery = new GetDeveloperByUserIdAsyncQuery(userId);
        var developer = developerQueryService.handle(getDeveloperByUserIdAsyncQuery);
        if (developer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var developerResource = DeveloperResourceFromEntityAssembler.toResourceFromEntity(developer.get());
        return ResponseEntity.ok(developerResource);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<DeveloperResource> updateDeveloperByUserId(@PathVariable Long userId, @RequestBody UpdateDeveloperResource resource) {
        var getDeveloperByUserIdAsyncQuery = new GetDeveloperByUserIdAsyncQuery(userId);
        var developer = developerQueryService.handle(getDeveloperByUserIdAsyncQuery);

        var updateDeveloperCommand = UpdateDeveloperCommandFromResourceAssembler.toCommandFromResource(developer.get().getUserId(), resource);
        var updatedDeveloper = developerCommandService.handle(updateDeveloperCommand);
        if (updatedDeveloper.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var developerResourceUpdated = DeveloperResourceFromEntityAssembler.toResourceFromEntity(updatedDeveloper.get());
        return ResponseEntity.ok(developerResourceUpdated);
    }

}
