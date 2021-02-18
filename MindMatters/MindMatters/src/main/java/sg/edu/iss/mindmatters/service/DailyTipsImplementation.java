package sg.edu.iss.mindmatters.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.mindmatters.model.DailyTips;
import sg.edu.iss.mindmatters.repo.DailyTipsRepository;

@Service
@Transactional
public class DailyTipsImplementation implements DailyTipsService {
	
	@Autowired
	DailyTipsRepository dtRepo;

	@Override
	public List<DailyTips> findTip() {
		return dtRepo.findTip();
	}

}
