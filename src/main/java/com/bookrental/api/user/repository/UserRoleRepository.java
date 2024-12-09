package com.bookrental.api.user.repository;

import com.bookrental.api.user.model.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UsersRoles,Long> {
}
