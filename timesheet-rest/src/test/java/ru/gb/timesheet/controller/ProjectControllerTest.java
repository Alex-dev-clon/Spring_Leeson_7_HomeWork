package ru.gb.timesheet.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.repository.ProjectRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {

    @Autowired
    ProjectRepository projectRepository;


    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void getByIdNotFound() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("projects/-2")
                    .retrieve()
                    .toBodilessEntity();
        });
    }


    @Test
    void getByIdAllOk() {
        Project expected = new Project();
        expected.setName("projectName");
        expected = projectRepository.save(expected);


        ResponseEntity<Project> actual = restClient.get()
                .uri("/projects/" + expected.getId())
                .retrieve()
                .toEntity(Project.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Project responseBody = actual.getBody();

        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.getId());
        assertEquals(expected.getName(), responseBody.getName());

    }

    @Test
    void testCreate (){
        Project toCreate = new Project();
        toCreate.setName("NewMan");
        toCreate = projectRepository.save(toCreate);

        ResponseEntity<Project> response = restClient.post()
                .uri("/projects")
                .body(toCreate)
                .retrieve()
                .toEntity(Project.class);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Project responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getName(), toCreate.getName());

        assertTrue(projectRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById (){
        Project toDelete = new Project();
        toDelete.setName("NewName");
        toDelete = projectRepository.save(toDelete);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/projects/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity();


        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertFalse(projectRepository.existsById(toDelete.getId()));
    }
}