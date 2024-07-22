package ru.gb.timesheet.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * Описание структуры json-ответа на REST-запросы.
 * Т.е. запросы, ответ на которые - JSON.
 */
@Data
public class Timesheet {

  private Long id;
  private Long projectId;
  private int minutes;
  private LocalDate createdAt;

}
