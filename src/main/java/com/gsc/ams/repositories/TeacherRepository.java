package com.gsc.ams.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gsc.ams.entities.Teacher;
import com.gsc.ams.entities.Pupil;
@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long>{
	
	
	@Query("FROM Pupil a WHERE a.teacher.id = ?1")
	List<Pupil> findArticlesByTeacher(long id);


}
