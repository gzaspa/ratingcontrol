package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by os199 on 12.10.2017.
 */
public interface GroupRepository extends CrudRepository<Group, Integer> {

}
