package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.Student;
import edu.chdtu.report.ratingcontrol.entity.Group;

import edu.chdtu.report.ratingcontrol.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by os199 on 11.10.2017.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    @Query("select s.name from Student s inner join s.group on s.id = s.group.id where s.group.id = :groupId and s.active = 'T' order by s.name")
    List<Student> findByGroup(@Param("groupId") int groupId);
}
