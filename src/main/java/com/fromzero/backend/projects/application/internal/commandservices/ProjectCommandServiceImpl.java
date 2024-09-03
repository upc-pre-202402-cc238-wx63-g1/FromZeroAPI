package com.fromzero.backend.projects.application.internal.commandservices;

import com.fromzero.backend.projects.domain.model.aggregates.Project;
import com.fromzero.backend.projects.domain.model.commands.AssignProjectDeveloperCommand;
import com.fromzero.backend.projects.domain.model.commands.CreateProjectCommand;
import com.fromzero.backend.projects.domain.model.commands.UpdateProjectCandidatesListCommand;
import com.fromzero.backend.projects.domain.model.commands.UpdateProjectProgressCommand;
import com.fromzero.backend.projects.domain.services.ProjectCommandService;
import com.fromzero.backend.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {
    private final ProjectRepository projectRepository;
    public ProjectCommandServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> handle(CreateProjectCommand command) {
        var project= new Project(command);

        this.projectRepository.save(project);

        project.getLanguages().addAll(command.languages());
        project.getFrameworks().addAll(command.frameworks());
        this.projectRepository.save(project);
        return Optional.of(project);
    }

    @Override
    public Optional<Project> handle(UpdateProjectCandidatesListCommand command) {
        var project = command.project();
        project.getCandidates().add(command.developer());
        this.projectRepository.save(project);
        return Optional.of(project);
    }

    @Override
    public Optional<Project> handle(AssignProjectDeveloperCommand command) {
        var project =command.project();
        project.setDeveloper(command.developer());
        project.getCandidates().clear();
        project.setState("En progreso");
        this.projectRepository.save(project);
        return Optional.of(project);
    }

    @Override
    public Optional<Project> handle(UpdateProjectProgressCommand command) {
        var project = command.project();
        project.setProgress(command.progress());
        this.projectRepository.save(project);
        return Optional.of(project);
    }
}