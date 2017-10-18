package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.Subject;
import edu.chdtu.report.ratingcontrol.entity.SubjectGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

import static edu.chdtu.report.ratingcontrol.controller.util.Constant.CREDIT;
import static edu.chdtu.report.ratingcontrol.controller.util.Constant.DIFF_CREDIT;
import static edu.chdtu.report.ratingcontrol.controller.util.Constant.EXAM;

/**
 * Created by user on 17.10.2017.
 */
public interface SubjectGroupRepository extends CrudRepository<SubjectGroup, Integer> {
    @Query("select sfg from SubjectGroup sfg join sfg.subject su join sfg.group gr "+
            "where su.semester=:semester and gr.id=:groupId and "+
            "(su.knowledgeControl.id="+EXAM+" or su.knowledgeControl.id="+
            CREDIT+" or su.knowledgeControl.id="+DIFF_CREDIT+") "+
            "order by su.name")
    public Set<SubjectGroup> findLectureSubjectsByGroupAndSemester(@Param("groupId") Integer groupId, @Param("semester") short semester);

}
