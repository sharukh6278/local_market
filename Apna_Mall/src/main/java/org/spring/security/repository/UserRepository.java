package org.spring.security.repository;

import org.spring.security.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email,String Password);
    //public User findByUserNameAndPassword(String userName, String password);

    //Book getBookListByEmail(String email);

}
