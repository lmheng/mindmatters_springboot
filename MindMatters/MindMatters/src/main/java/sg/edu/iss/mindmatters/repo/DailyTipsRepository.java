package sg.edu.iss.mindmatters.repo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.mindmatters.model.DailyTips;

public interface DailyTipsRepository extends JpaRepository<DailyTips, Long> {

	@Query(value = "SELECT * FROM daily_tips ORDER BY RAND() LIMIT 1", nativeQuery = true)
	List<DailyTips> findTip();

}
