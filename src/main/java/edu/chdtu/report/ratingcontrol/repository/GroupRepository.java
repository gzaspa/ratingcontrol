package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

import static edu.chdtu.report.ratingcontrol.controller.util.Constant.CREDIT;
import static edu.chdtu.report.ratingcontrol.controller.util.Constant.DIFF_CREDIT;
import static edu.chdtu.report.ratingcontrol.controller.util.Constant.EXAM;

/**
 * Created by os199 on 12.10.2017.
 */
public interface GroupRepository extends CrudRepository<Group, Integer> {
    @Query("select gr from Group gr where gr.creationYear-gr.startYear=:currentYear-:year and gr.tuitionForm='д'")
    public Set<Group> findGroupsByYear(@Param("currentYear") short currentYear, @Param("year") short year);

}
