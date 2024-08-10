package ru.gb.timesheet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

  // GET - получить - не содержит тела
  // POST - create
  // PUT - изменение
  // PATCH - изменение
  // DELETE - удаление

  // @GetMapping("/timesheets/{id}") // получить конкретную запись по идентификатору
  // @DeleteMapping("/timesheets/{id}") // удалить конкретную запись по идентификатору
  // @PutMapping("/timesheets/{id}") // обновить конкретную запись по идентификатору

  private final TimesheetService service;

  public TimesheetController(TimesheetService service) {
    this.service = service;
  }

  @Operation(
          summary = "Получить timesheet",
          description = "Получить timesheet по id",
          responses = {
                  @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
                  @ApiResponse(description = "Timesheet не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
          }
  )
  @GetMapping("/{id}")
  public ResponseEntity<Timesheet> get(@PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // /timesheets
  // /timesheets?createdAtBefore=2024-07-09
  // /timesheets?createdAtAfter=2024-07-15
  // /timesheets?createdAtAfter=2024-07-15&createdAtBefore=2024-06-05
  @Operation(
          summary = "Получить список timesheets",
          description = "Получить все timesheets",
          responses = {
                  @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
                  @ApiResponse(description = "Список не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
          }
  )
  @GetMapping
  public ResponseEntity<List<Timesheet>> getAll(
    @RequestParam(required = false) LocalDate createdAtBefore,
    @RequestParam(required = false) LocalDate createdAtAfter
  ) {
    return ResponseEntity.ok(service.findAll(createdAtBefore, createdAtAfter));
  }

  // client -> [spring-server -> ... -> TimesheetController
  //                          -> exceptionHandler(e)
  // client <- [spring-server <- ...

  @Operation(
          summary = "Создать timesheet",
          description = "Создать новый timesheet",
          responses = {
                  @ApiResponse(description = "Успешный ответ", responseCode = "201", content = @Content(schema = @Schema(implementation = Project.class)))
          }
  )
  @PostMapping // создание нового ресурса
  public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
    final Timesheet created = service.create(timesheet);

    // 201 Created
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @Operation(
          summary = "Удалить timesheet",
          description = "Удалить timesheet по id",
          responses = {
                  @ApiResponse(description = "Успешный ответ", responseCode = "204", content = @Content(schema = @Schema(implementation = Project.class)))
          }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);

    // 204 No Content
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
    return ResponseEntity.notFound().build();
  }

}
