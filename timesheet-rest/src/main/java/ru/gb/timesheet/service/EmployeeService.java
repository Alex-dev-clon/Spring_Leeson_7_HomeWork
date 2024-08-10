package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.EmployeeRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TimesheetRepository timesheetRepository;


    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id){
        return employeeRepository.findById(id);
    }

    public Employee create(Employee employee){
        return employeeRepository.save(employee);
    }
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Timesheet> getTimesheets(Long id) {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Employee with id = " + id + " does not exists");
        }
        //throw new UnsupportedOperationException();
        return timesheetRepository.findByEmployeeId(id);
    }



}