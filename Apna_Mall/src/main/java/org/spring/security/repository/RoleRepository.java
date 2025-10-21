package org.spring.security.repository;

import org.spring.security.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByName(String name);
    public List<Role> findRoleByUserEmail(String email);

}
