package sg.edu.iss.mindmatters.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import sg.edu.iss.mindmatters.model.DailyTips;

public interface DailyTipsService {
	
	public List<DailyTips> findTip();

}
