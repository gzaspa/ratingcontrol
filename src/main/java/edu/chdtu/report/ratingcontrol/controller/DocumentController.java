package edu.chdtu.report.ratingcontrol.controller;

import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Subject;
import edu.chdtu.report.ratingcontrol.entity.SubjectGroup;
import edu.chdtu.report.ratingcontrol.repository.CurrentYearRepository;
import edu.chdtu.report.ratingcontrol.repository.GroupRepository;
import edu.chdtu.report.ratingcontrol.repository.SubjectGroupRepository;
import edu.chdtu.report.ratingcontrol.service.document.RatingReportDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by os199 on 12.10.2017.
 */
@Controller
public class DocumentController {
    @Autowired
    RatingReportDocService ratingReportDocService;

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/ratingcontrol")
    public @ResponseBody String ratingControl(@RequestParam short year, @RequestParam short semester){
        ratingReportDocService.makeDocument(year, semester);
        return "done";
    }
}
