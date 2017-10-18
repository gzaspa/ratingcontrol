package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import static edu.chdtu.report.ratingcontrol.controller.util.Constant.*;
import java.util.Set;

/**
 * Created by os199 on 11.10.2017.
 */
public interface SubjectRepository extends CrudRepository<Subject, Integer>{
    @Query("select su from Subject su join su.groups gr "+
            "where su.semester=:semester and gr.id=:groupId and "+
            "(su.knowledgeControl.id="+EXAM+" or su.knowledgeControl.id="+
            CREDIT+" or su.knowledgeControl.id="+DIFF_CREDIT+") "+
            "order by su.name")
    public Set<Subject> findLectureSubjectsByGroupAndSemester(@Param("groupId") Integer groupId, @Param("semester") short semester);
}
