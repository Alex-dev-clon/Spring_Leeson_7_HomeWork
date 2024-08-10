package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.gb.timesheet.page.EmployeePageDto;
import ru.gb.timesheet.page.ProjectPageDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeePageService {
//    private final EmployeeService employeeService;
//
//
//    public Optional<EmployeePageDto> findById(Long id){
//        return employeeService.findById(id)
//                .map(this::convert);
//    }
//
//    private EmployeePageDto convert(Employee employee){
//        EmployeePageDto employeePageDto = new EmployeePageDto();
//
//        employeePageDto.setId(String.valueOf(employee.getId()));
//        employeePageDto.setName(employee.getName());
//
//        return employeePageDto;
//    }
}