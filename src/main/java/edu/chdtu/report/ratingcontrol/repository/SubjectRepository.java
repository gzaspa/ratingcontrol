package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by os199 on 11.10.2017.
 */
public interface SubjectRepository extends CrudRepository<Subject, Long>{

}
