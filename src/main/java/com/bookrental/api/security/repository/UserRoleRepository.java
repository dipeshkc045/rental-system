package com.bookrental.api.security.repository;

import com.bookrental.api.security.model.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UsersRoles,Long> {
}
