package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.timesheet.model.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet,Long> {
    //select * from timesheet where project_id = $1
    //Note:сломается , если в БД результат выдает больше одного значения
    // Optional<Timesheet> findByProjectId(Long projectId);

    //select * from timesheet where project_id = $1
    //List<Timesheet> findByProjectId(Long projectId);

    //select * from timesheet where project_id = $1 or minutes = $2
    //List<Timesheet> findProjectIdOrMinutes(Long projectId, Integer minutes);

    //select * from timesheet where createdAt > $1 and createdAt < $1
    List<Timesheet> findByCreatedAtBetween(LocalDate min, LocalDate max);

    //jql - java query language
    @Query("select t from Timesheet t where t.projectId = :projectId order by t.createdAt desc")
    List<Timesheet> findByProjectId(Long projectId);

    @Query("select t from Timesheet t where t.employeeId = :employeeId order by t.createdAt desc")
    List<Timesheet> findByEmployeeId(Long employeeId);

}
