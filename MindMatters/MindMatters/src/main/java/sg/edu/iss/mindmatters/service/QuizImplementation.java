package sg.edu.iss.mindmatters.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.mindmatters.model.QuizOutcome;
import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.repo.QuizRepository;
import sg.edu.iss.mindmatters.repo.UserRepository;

@Service
@Transactional
public class QuizImplementation implements QuizServices {

	@Autowired
	QuizRepository qrepo;

	@Autowired
	UserRepository urepo;

	@Override
	public void saveQuizOutcome(String outcome, User user) {
		if (user != null) {

			if (checkAttempt(user)) {
				QuizOutcome qo = qrepo.findByUser(user.getId());
				qo.setNextQuiz(LocalDate.now().plusMonths(3));
				qo.setQuizOutcome(outcome);
				qrepo.save(qo);
			} else {
				QuizOutcome qo = new QuizOutcome(LocalDate.now().plusMonths(3), outcome, user);
				qrepo.save(qo);
			}
		}

	}


	@Override
	public boolean checkAttempt(User user) {

		if (qrepo.findByUser(user.getId()) != null)
			return true;

		return false;
	}

	@Override
	public QuizOutcome findByEmail(String userEmail) {
		return qrepo.findByEmail(userEmail);
	}

}
