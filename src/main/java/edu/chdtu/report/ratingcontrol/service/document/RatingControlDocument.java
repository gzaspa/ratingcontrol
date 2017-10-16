package edu.chdtu.report.ratingcontrol.service.document;


import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Student;
import edu.chdtu.report.ratingcontrol.entity.Subject;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import java.io.*;
import java.util.List;
import java.util.Set;

public class RatingControlDocument {
    private static final String RESULT_PATH = "C:\\Users\\os199\\Desktop\\deanoffice documents\\ratingcontrol\\rcresult.docx";
    private static final String TEMPLATE_PATH = "C:\\Users\\os199\\Desktop\\deanoffice documents\\ratingcontrol\\rctemplate.docx";
    private static final String STUDENT_INDEX_PLACEHOLDER = "%%n";
    private static final String STUDENT_NAME_PLACEHOLDER = "%%studentName";
    private static final String SUBJECT_PLACEHOLDER = "%%subjectName";
    private static final String COURSE_PLACEHOLDER = "%%course";
    private static final int COUNT_OF_SUBJECTS_PLACEHOLDERS = 10;

    private FileInputStream fis;
    private XWPFDocument document;
    private List<XWPFTable> tables;
    private XWPFTable table;
    private FileOutputStream out;
    private Group group;
    private Set<Subject> subjects;
    private short currentYear;
    //    @Autowired
//    private StudentRepository studentRepository;

    public RatingControlDocument(Group group, Set<Subject> subjects, short currentYear){
        try {
            this.fis = new FileInputStream(TEMPLATE_PATH);
            this.document = new XWPFDocument(fis);
            this.out = new FileOutputStream( new File(RESULT_PATH));
            this.tables = document.getTables();
            this.group = group;
            this.subjects = subjects;
            this.currentYear = currentYear;
        } catch (IOException e) {
            e.printStackTrace();
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
    }

    private void fillTable(){
//        replaceTextInDocument();
        table = tables.get(0);
        table = replaceTextInTable(table, "%%curator", "");
        addStudentRows(group.getStudents());
        replaceSubjects();
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
        for (Subject subject: subjects){
            replaceTextInTable(table, SUBJECT_PLACEHOLDER, subject.getName());
        }
        XWPFTableRow subjectsRow = findRowByPlaceholder(table, SUBJECT_PLACEHOLDER);
        for (int i = 0; i<COUNT_OF_SUBJECTS_PLACEHOLDERS-subjects.size(); i++) {
            replaceTextInCell(subjectsRow, SUBJECT_PLACEHOLDER, "");
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
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(target)) {
                        text = text.replace(target, placement);
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

    private void fillCourse(){
        int course = currentYear - group.getCreationYear() + 1;
        replaceTextInDocument(COURSE_PLACEHOLDER, ""+course);
    }

}
