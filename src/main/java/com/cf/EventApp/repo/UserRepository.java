package com.cf.EventApp.repo;

import com.cf.EventApp.models.Provider;
import com.cf.EventApp.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.provider = ?2 WHERE u.username = ?1")
    void updateProvider(String username, Provider provider);
}
