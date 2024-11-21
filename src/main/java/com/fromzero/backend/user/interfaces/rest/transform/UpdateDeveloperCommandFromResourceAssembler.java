package com.fromzero.backend.user.interfaces.rest.transform;

import com.fromzero.backend.user.domain.model.commands.UpdateDeveloperCommand;
import com.fromzero.backend.user.interfaces.rest.resources.UpdateDeveloperResource;

public class UpdateDeveloperCommandFromResourceAssembler {
    public static UpdateDeveloperCommand toCommandFromResource(Long userId, UpdateDeveloperResource resource) {
        return new UpdateDeveloperCommand(
                userId,
                resource.firstName(),
                resource.lastName(),
                resource.description(),
                resource.country(),
                resource.phone(),
                resource.specialties(),
                resource.profileImgUrl()
        );
    }
}
