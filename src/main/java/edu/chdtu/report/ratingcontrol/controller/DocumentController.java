package edu.chdtu.report.ratingcontrol.controller;

import edu.chdtu.report.ratingcontrol.controller.util.EmptyJsonResponse;
import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Subject;
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

import java.util.List;

/**
 * Created by os199 on 12.10.2017.
 */
@Controller
public class DocumentController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/ratingcontrol")
    public @ResponseBody Group ratingControl(@RequestParam int groupId, @RequestParam short semester){
        Group group = groupRepository.readGroupByIdAndSubjectSemester(semester ,groupId);
        RatingControlDocument document = new RatingControlDocument(group);
        document.fillTable();
        document.closeDocument();
        return group;
    }
}
