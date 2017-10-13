package edu.chdtu.report.ratingcontrol;

import edu.chdtu.report.ratingcontrol.entity.Group;
import edu.chdtu.report.ratingcontrol.entity.Student;
import edu.chdtu.report.ratingcontrol.entity.Subject;
import edu.chdtu.report.ratingcontrol.repository.StudentRepository;
import edu.chdtu.report.ratingcontrol.repository.SubjectRepository;
import edu.chdtu.report.ratingcontrol.service.document.RatingControlDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.List;

@SpringBootApplication
public class RatingControlApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RatingControlApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(RatingControlApplication.class, args);

	}
}
