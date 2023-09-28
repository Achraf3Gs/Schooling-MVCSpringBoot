package com.gsc.ams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gsc.ams.entities.Pupil;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, Long>{

}
