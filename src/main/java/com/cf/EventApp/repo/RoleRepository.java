package com.cf.EventApp.repo;

import com.cf.EventApp.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.name = :role")
    public Role getRoleByRole(@Param("role") String role);

}
