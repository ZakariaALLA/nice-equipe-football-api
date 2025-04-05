package com.francefootball.niceequipefootballapi.persistence.repository;

import com.francefootball.niceequipefootballapi.metier.enums.EnumRoles;
import com.francefootball.niceequipefootballapi.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRoles name);
}
