package edu.chdtu.report.ratingcontrol.repository;

import edu.chdtu.report.ratingcontrol.entity.CurrentYear;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by os199 on 16.10.2017.
 */
public interface CurrentYearRepository extends CrudRepository <CurrentYear, Short>{
    @Query("select currentYear from CurrentYear")
    short findFirst();
}
