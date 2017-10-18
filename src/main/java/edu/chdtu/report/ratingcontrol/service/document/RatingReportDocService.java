package edu.chdtu.report.ratingcontrol.service.document;


import edu.chdtu.report.ratingcontrol.entity.*;
import edu.chdtu.report.ratingcontrol.repository.CurrentYearRepository;
import edu.chdtu.report.ratingcontrol.repository.GroupRepository;
import edu.chdtu.report.ratingcontrol.repository.SubjectGroupRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RatingReportDocService {
    private static final String RESULT_PATH = "C:\\Users\\user\\Desktop\\deanoffice documents\\ratingcontrol\\";
    private static final String TEMPLATE_PATH = "C:\\Users\\user\\Desktop\\deanoffice documents\\ratingcontrol\\rctemplate.docx";
    private static final String STUDENT_INDEX_PLACEHOLDER = "%%n";
    private static final String STUDENT_NAME_PLACEHOLDER = "%%studentName";
    private static final String SUBJECT_PLACEHOLDER = "%%subjectName";
    private static final String TEACHER_PLACEHOLDER = "teacher";
    private static final String COURSE_PLACEHOLDER = "%course";
    private static final String YEAR_PLACEHOLDER = "year";
    private static final String GROUP_PLACEHOLDER = "group";
    private static final String SEMESTER_PLACEHOLDER = "semester";
    private static final int COUNT_OF_SUBJECTS_PLACEHOLDERS = 10;

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SubjectGroupRepository subjectGroupRepository;
    @Autowired
    private CurrentYearRepository currentYearRepository;

    private FileInputStream fis;
    private XWPFDocument document;
    private List<XWPFTable> tables;
    private XWPFTable table;
    private FileOutputStream out;
    private Set<SubjectGroup> subjectsGroup;
    private Group group;
    private short semester;
    private short currentYear;

    public void makeDocument(short year, short semester) {
//        boolean first = true;
//        CTBody body = document.getDocument().getBody();
//        String srcString = body.xmlText();
        this.semester = semester;
        this.currentYear = currentYearRepository.findFirst();
        Set<Group> groups = groupRepository.findGroupsByYear(currentYear, year);
        for(Group group: groups) {
            this.group = group;
            this.subjectsGroup = subjectGroupRepository.findLectureSubjectsByGroupAndSemester(group.getId(), (short)((year-1)*2+semester));
            try {
                this.fis = new FileInputStream(TEMPLATE_PATH);
                this.document = new XWPFDocument(fis);
                this.out = new FileOutputStream(new File(RESULT_PATH + group.getName() + ".docx"));
                this.tables = document.getTables();
                fillDocument();
                closeDocument();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            body = document.getDocument().getBody();
//            try {
//                appendBody(body, srcString, first);
//            } catch (XmlException e) {
//                e.printStackTrace();
//            }
//            first = false;
//            if(!first)
//                break;
        }
    }

    public void closeDocument(){
        try {
            this.document.write(out);
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillDocument(){
        fillTable();
        fillCourse();
        fillYear();
        fillGroup();
        fillSemester();
    }

    private void fillTable(){
//        replaceTextInDocument();
        table = tables.get(0);
        table = replaceTextInTable(table, "%%curator", "");
        addStudentRows(group.getStudents());
        replaceSubjects();
        replaceTeachers();
    }

    private XWPFTable replaceTextInTable(XWPFTable tbl, String target, String replacement){
        m1:
        for (XWPFTableRow row : tbl.getRows()) {
            if (isCellByPlaceholder(row, target)) {
                replaceTextInCell(row, target, replacement);
                break m1;
            }
        }
        return tbl;
    }

    private void addStudentRows(List<Student> students){
        XWPFTableRow studentRow = findRowByPlaceholder(table, STUDENT_INDEX_PLACEHOLDER);
//        table.removeRow();
        for (int i = 0; i<students.size(); i++){
            try {
                Student student = students.get(i);
                String studentName = student.getSurname()+" "+ student.getName();
                int index = i+1;
                if (i!=students.size()-1) {
                    CTRow ctrow = CTRow.Factory.parse(studentRow.getCtRow().newInputStream());
                    XWPFTableRow newRow = new XWPFTableRow(ctrow, table);
                    replaceTextInCell(newRow, STUDENT_NAME_PLACEHOLDER, studentName);
                    replaceTextInCell(newRow, STUDENT_INDEX_PLACEHOLDER, "" + index + ".");
                    table.addRow(newRow, table.getRows().size() - 2);
                }
                else {
                    replaceTextInCell(studentRow, STUDENT_NAME_PLACEHOLDER, studentName);
                    replaceTextInCell(studentRow, STUDENT_INDEX_PLACEHOLDER, "" + index + ".");
                }
            } catch (XmlException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void replaceSubjects(){
        for (SubjectGroup subjectGroup: subjectsGroup){
            replaceTextInTable(table, SUBJECT_PLACEHOLDER, subjectGroup.getSubject().getName());
        }
        XWPFTableRow subjectsRow = findRowByPlaceholder(table, SUBJECT_PLACEHOLDER);
        for (int i = 0; i<COUNT_OF_SUBJECTS_PLACEHOLDERS-subjectsGroup.size(); i++) {
            replaceTextInCell(subjectsRow, SUBJECT_PLACEHOLDER, "");
        }
    }

    private void replaceTeachers(){
        for (SubjectGroup subjectGroup: subjectsGroup){
            Teacher teacher = subjectGroup.getTeacher();
            String teacherName = "";
            if (teacher != null)
                teacherName = teacher.getSurname()+" "+teacher.getName().substring(0,1)+"."+teacher.getPatronimic().substring(0,1)+".";
            replaceTextInTable(table, TEACHER_PLACEHOLDER, teacherName);
        }
        XWPFTableRow subjectsRow = findRowByPlaceholder(table, TEACHER_PLACEHOLDER);
        for (int i = 0; i<COUNT_OF_SUBJECTS_PLACEHOLDERS-subjectsGroup.size(); i++) {
            replaceTextInCell(subjectsRow, TEACHER_PLACEHOLDER, "");
        }
    }

    private XWPFTableRow findRowByPlaceholder(XWPFTable tbl, String placeholder){
        for (XWPFTableRow row : tbl.getRows()) {
            for (XWPFTableCell cell: row.getTableCells()) {
                for (XWPFParagraph p : cell.getParagraphs()) {
                    String text = p.getText();
                    if (text != null && text.contains(placeholder)) {
                        return row;
                    }
                }
            }
        }
        return null;
    }

    private int findIndexCell (XWPFTableRow row, String placeholder){
        for (int i = 0; i<row.getTableCells().size();i++) {
            XWPFTableCell cell = row.getTableCells().get(i);
            for (XWPFParagraph p : cell.getParagraphs()) {
                String text = p.getText();
                if (text != null && text.contains(placeholder)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void replaceTextInCell(XWPFTableRow row, String target, String replacement){
        m1:
        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph p : cell.getParagraphs()) {
                    String text = p.getText();
                    if (text != null && text.contains(target)) {
                        List<XWPFRun> runs = p.getRuns();
                        for(int i = runs.size() - 1; i > 0; i--) {
                            p.removeRun(i);
                        }
                        p.getRuns().get(0).setText(replacement,0);
//                        r.setText(text,0);
                        break m1;
                    }
                }

        }
    }

    private boolean isCellByPlaceholder(XWPFTableRow row, String placeholder){
        for (int i = 0; i<row.getTableCells().size();i++) {
            XWPFTableCell cell = row.getTableCells().get(i);
            for (XWPFParagraph p : cell.getParagraphs()) {
                String text = p.getText();
                if (text != null && text.contains(placeholder)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void replaceTextInDocument(String target, String placement){
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                String text = new String();
                for (XWPFRun r : runs) {
                    text = r.getText(0);
                    if (text != null && text.contains(target)) {
                        text = text.replace(target, placement);
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

//    private void replaceTextInDocument(String target, String placement){
//        for (XWPFParagraph p : document.getParagraphs()) {
//            List<XWPFRun> runs = p.getRuns();
//            if (runs != null) {
//                String text = new String();
//                for (XWPFRun r : runs) {
//                    text += r.getText(0);
//                }
//                if (text.contains(target)) {
//                    text = text.replace(target, placement);
//                    p.getRuns().get(0).setText(text, 0);
//                }
//            }
//        }
//    }

    private void fillCourse(){
        int course = currentYear - group.getCreationYear() + group.getStartYear();
        replaceTextInDocument(COURSE_PLACEHOLDER, ""+course);
    }

    private void fillYear() {
        replaceTextInDocument(YEAR_PLACEHOLDER, "" + currentYear+"/"+(currentYear+1));
    }

    private void fillGroup() {
        replaceTextInDocument(GROUP_PLACEHOLDER, "" + group.getName());
    }

    private void fillSemester() {
        replaceTextInDocument(SEMESTER_PLACEHOLDER, "" + (semester%2==0?2:1));
    }

    private void appendBody(CTBody src, String append, boolean first) throws XmlException {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);

        final String mainPart;
        // exclude template itself in first appending
        if(first) {
            mainPart = "";
        } else {
            mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        }

        String suffix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = append.substring(append.indexOf(">") + 1, append.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+suffix);
        src.set(makeBody);
    }
}
