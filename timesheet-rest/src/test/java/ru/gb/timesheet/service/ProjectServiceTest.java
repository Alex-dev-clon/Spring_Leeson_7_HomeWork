package ru.gb.timesheet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = {ProjectService.class, ProjectRepository.class, TimesheetRepository.class})
@ActiveProfiles("test")
@SpringBootTest
class ProjectServiceTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Test
    void findByIdEmpty() {
        assertFalse(projectRepository.existsById(2L));
        assertTrue(projectService.findById(2L).isEmpty());
    }

    @Test
    void findByIdPresent() {
        Project project = new Project();
        project.setName("projectName");
        project = projectRepository.save(project);

        Optional<Project> actual = projectService.findById(project.getId());
        assertTrue(actual.isPresent());
        assertEquals(actual.get().getId(), project.getId());
        assertEquals(actual.get().getName(), project.getName());
    }
}