package edu.chdtu.report.ratingcontrol.controller;

import edu.chdtu.report.ratingcontrol.controller.util.EmptyJsonResponse;
import edu.chdtu.report.ratingcontrol.entity.CurrentYear;
import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Subject;
import edu.chdtu.report.ratingcontrol.repository.CurrentYearRepository;
import edu.chdtu.report.ratingcontrol.repository.GroupRepository;
import edu.chdtu.report.ratingcontrol.repository.SubjectRepository;
import edu.chdtu.report.ratingcontrol.service.document.RatingControlDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by os199 on 12.10.2017.
 */
@Controller
public class DocumentController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CurrentYearRepository currentYearRepository;

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/ratingcontrol")
    public @ResponseBody String ratingControl(@RequestParam short year, @RequestParam short semester){
        short currentYear = currentYearRepository.findFirst();
        Set<Group> groups = groupRepository.findGroupsByYear(currentYear, year);
        Map<Group,Set<Subject>> data = new HashMap<Group,Set<Subject>>();
        for (Group group: groups){
            Set<Subject> subjects = subjectRepository.findLectureSubjectsByGroupAndSemester(group.getId(), (short)((year-1)*2+semester));
            data.put(group, subjects);
        }
        RatingControlDocument document = new RatingControlDocument(data, semester, currentYear);
        document.makeDocument();
        return ""+groups.size();
    }
}
