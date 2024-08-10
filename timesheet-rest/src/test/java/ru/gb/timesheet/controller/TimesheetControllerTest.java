package ru.gb.timesheet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimesheetControllerTest {

    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    ProjectRepository projectRepository;

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        timesheetRepository.deleteAll();
    }

    @Test
    void getAllTimesheets() {

        Timesheet expected1 = new Timesheet();
        expected1.setId(1L);
        expected1.setEmployeeId(1L);
        expected1.setProjectId(1L);
        expected1.setMinutes(10);

        Timesheet expected2 = new Timesheet();
        expected2.setId(2L);
        expected2.setEmployeeId(2L);
        expected2.setProjectId(2L);
        expected2.setMinutes(20);

        timesheetRepository.save(expected1);
        timesheetRepository.save(expected2);

        ResponseEntity<List<Timesheet>> entity = restClient.get()
                .uri("/timesheets")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Timesheet>>() {
                });

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<Timesheet> responseBody = entity.getBody();
//        assertEquals(expected1.getId(), responseBody.get(0).getId());
//        assertEquals(expected1.getMinutes(), responseBody.get(0).getMinutes());
//        assertEquals(expected1.getCreatedAt(), responseBody.get(0).getCreatedAt());
//        assertEquals(expected1.getProjectId(), responseBody.get(0).getProjectId());
//        assertEquals(expected1.getEmployeeId(), responseBody.get(0).getEmployeeId());
//
//        assertEquals(expected2.getId(), responseBody.get(1).getId());
//        assertEquals(expected2.getMinutes(), responseBody.get(1).getMinutes());
//        assertEquals(expected2.getCreatedAt(), responseBody.get(1).getCreatedAt());
//        assertEquals(expected2.getProjectId(), responseBody.get(1).getProjectId());
//        assertEquals(expected2.getEmployeeId(), responseBody.get(1).getEmployeeId());

        assertEquals(timesheetRepository.findAll(), responseBody);
    }

    @Test
    void getById() {
        Timesheet expected = new Timesheet();
        expected.setId(1L);
        expected.setEmployeeId(1L);
        expected.setProjectId(1L);
        expected.setMinutes(10);

        timesheetRepository.save(expected);

        ResponseEntity<Timesheet> actual = restClient.get()
                .uri("/timesheets/" + expected.getId())
                .retrieve()
                .toEntity(Timesheet.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Timesheet responseBody = actual.getBody();

        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.getId());
        assertEquals(expected.getMinutes(), responseBody.getMinutes());
        assertEquals(expected.getCreatedAt(), responseBody.getCreatedAt());
        assertEquals(expected.getProjectId(), responseBody.getProjectId());
        assertEquals(expected.getEmployeeId(), responseBody.getEmployeeId());
    }

    @Test
    void testCreate(){
        Project project = new Project();
        project.setId(1L);
        project.setName("NewName");
        projectRepository.save(project);

        Timesheet toCreate = new Timesheet();
        toCreate.setId(1L);
        toCreate.setEmployeeId(1L);
        toCreate.setProjectId(1L);
        toCreate.setMinutes(10);

        ResponseEntity<Timesheet> response = restClient.post()
                .uri("/timesheets")
                .body(toCreate)
                .retrieve()
                .toEntity(Timesheet.class);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Timesheet responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getId(), toCreate.getId());

        assertTrue(timesheetRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById (){
        Timesheet toDelete = new Timesheet();
        toDelete.setId(1L);
        toDelete.setEmployeeId(1L);
        toDelete.setProjectId(1L);
        toDelete.setMinutes(10);
        timesheetRepository.save(toDelete);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/projects/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity();


        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(projectRepository.existsById(toDelete.getId()));
    }


    @Test
    void testPutById(){
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setId(1L);
        timesheet1.setMinutes(1000);
        timesheet1.setProjectId(1000L);
        timesheet1.setEmployeeId(1000L);
        timesheetRepository.save(timesheet1);

        Timesheet timesheet2 = new Timesheet();
        timesheet2.setId(timesheet1.getId());
        timesheet2.setMinutes(2000);
        timesheet2.setProjectId(2000L);
        timesheet2.setEmployeeId(2000L);

        ResponseEntity<Timesheet> response = restClient.put()
                .uri("/timesheets/" + timesheet1.getId())
                .body(timesheet2)
                .retrieve()
                .toEntity(Timesheet.class);

        Timesheet responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(timesheet1.getId(), responseBody.getId());
    }



}
