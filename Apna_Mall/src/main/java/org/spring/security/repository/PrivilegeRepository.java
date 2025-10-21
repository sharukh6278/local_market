package org.spring.security.repository;

import org.spring.security.entity.auth.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    public Privilege findByName(String name);
}
