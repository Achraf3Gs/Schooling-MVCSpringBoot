package com.gsc.ams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gsc.ams.entities.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
 User findByEmail(String email);
}

