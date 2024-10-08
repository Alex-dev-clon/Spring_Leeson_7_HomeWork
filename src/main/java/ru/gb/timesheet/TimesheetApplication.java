package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.timesheet.model.*;
import ru.gb.timesheet.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {

	/**
	 * spring-data - ...
	 * spring-data-jdbc - зависимость, которая предоставляет удобные преднастроенные инструемнты
	 * для работы c реляционными БД
	 * spring-data-jpa - зависимость, которая предоставляет удобные преднастроенные инструемнты
	 * для работы с JPA
	 *      spring-data-jpa
	 *       /
	 *     /
	 *   jpa   <------------- hibernate (ecliselink ...)
	 *
	 * spring-data-mongo - завимость, которая предоставляет инструменты для работы с mongo
	 * spring-data-aws
	 *
	 *
	 */


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		Role roleAdmins = new Role();
		roleAdmins.setName("admin");
		roleRepository.save(roleAdmins);

		Role roleUsers = new Role();
		roleUsers.setName("user");
		roleRepository.save(roleUsers);

		Role roleRest = new Role();
		roleRest.setName("rest");
		roleRepository.save(roleRest);

		UserRepository userRepository = ctx.getBean(UserRepository.class);
		User user = new User();
		user.setLogin("user"); //user
		user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq");

		user.getRoles().add(roleUsers);
		roleUsers.getUsers().add(user);
		userRepository.save(user);

		User admin = new User();
		admin.setLogin("admin"); // admin
		admin.setPassword("$2a$12$QPoOV5Qr8sRD5lkcugid8ezyLwZmcFt2j0kh6XLsPf/IgkmLxe5SK");
		admin.getRoles().add(roleUsers);
		admin.getRoles().add(roleAdmins);
		roleUsers.getUsers().add(admin);
		roleAdmins.getUsers().add(admin);
		userRepository.save(admin);

		User rest = new User();
		rest.setLogin("rest"); // rest
		rest.setPassword("$2a$12$tZaB13uZJJkD9zgpSiwZXeeYtOY/vjDR9XQwm2woqGHpmxhQXeTYq");
		rest.getRoles().add(roleRest);
		roleUsers.getUsers().add(rest);
		userRepository.save(rest);


//// 1. пользователь регистрируется на сайте и вводит свой пароль
//		// 1.1 сервер считает хеш от пароля и сохраняет его в бд = hashInDatabase
//		// 2. пользователь логинится на сайт и вводит свой пароль
//		// 2.1 сервер считает хеш от пароля и сравнивает его с хешом в БД
//		// hashFunc(password) -> hashInDatabase
//		// hash -> password !!!
//		// username1, password => hash(username1 + _ + password) == hash1
//		// username2, password => hash(username2 + _ + password) == hash2
//
//		// hashFunc(rawPassword) == hashInDatabase
//
//		UserRepository userRepository = ctx.getBean(UserRepository.class);
//		User admin = new User();
//		admin.setLogin("admin");
//		admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin
//		User user = new User();
//		user.setLogin("user");
//		user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user
//		User anonymous = new User();
//		anonymous.setLogin("anon");
//		anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon
//		admin = userRepository.save(admin);
//		user = userRepository.save(user);
//		anonymous = userRepository.save(anonymous);
//
//		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
//		// id user_id role_name
//		//  1       1     admin
//		//  2       1     user
//		//  3       2     user
//		UserRole adminAdminRole = new UserRole();
//		adminAdminRole.setUserId(admin.getId());
//		adminAdminRole.setRoleName(Role.ADMIN.getName());
//		userRoleRepository.save(adminAdminRole);
//
//		UserRole adminUserRole = new UserRole();
//		adminUserRole.setUserId(admin.getId());
//		adminUserRole.setRoleName(Role.USER.getName());
//		userRoleRepository.save(adminUserRole);
//
//		UserRole userUserRole = new UserRole();
//		userUserRole.setUserId(user.getId());
//		userUserRole.setRoleName(Role.USER.getName());
//		userRoleRepository.save(userUserRole);


//		JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
//		jdbcTemplate.execute("delete from project");

		ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);

		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setName("Project #" + i);
			projectRepo.save(project);
		}

		TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

		LocalDate createdAt = LocalDate.now();
		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

			timesheetRepo.save(timesheet);
		}


		EmployeeRepository employeeRepo = ctx.getBean(EmployeeRepository.class);

		for (int i = 1; i <= 10; i++) {
			Employee employee = new Employee();
			employee.setName("Employee #" + i);
			employeeRepo.save(employee);
		}
	}

}
