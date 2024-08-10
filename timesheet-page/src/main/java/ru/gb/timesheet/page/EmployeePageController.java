package ru.gb.timesheet.page;

//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.gb.timesheet.model.Employee;
//import ru.gb.timesheet.service.EmployeePageService;
//import ru.gb.timesheet.service.EmployeeService;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/home/employees")
//@RequiredArgsConstructor
//public class EmployeePageController {
//    private final EmployeePageService service;
//
//
//    @GetMapping("/{id}")
//    public String findById(@PathVariable Long id, Model model){
//        Optional<EmployeePageDto> emplDto = service.findById(id);
//        if (emplDto.isEmpty()) {
//            throw new NoSuchElementException();
//        }
//        model.addAttribute("employee",emplDto.get());
//        return "employee-page.html";
//    }
//
//}