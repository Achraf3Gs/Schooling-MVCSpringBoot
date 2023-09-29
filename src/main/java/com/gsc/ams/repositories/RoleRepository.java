package com.gsc.ams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gsc.ams.entities.Role;
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
Role findByRole(String role);

}
