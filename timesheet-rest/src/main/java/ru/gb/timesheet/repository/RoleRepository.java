package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.timesheet.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(nativeQuery = true, value =
            "select r.name from users_roles u join role r on u.role_id = r.id " +
                    "where u.user_id = :userId")
    List<String> findUserRolesByUserId(Long userId);

}

