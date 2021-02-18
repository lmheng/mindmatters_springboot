package sg.edu.iss.mindmatters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.mindmatters.model.QuizOutcome;

public interface QuizRepository extends JpaRepository<QuizOutcome, Integer>{

	@Query("SELECT qo FROM QuizOutcome qo WHERE user.id = :uid")
	QuizOutcome findByUser(@Param("uid") long id);

	@Query("SELECT qo FROM QuizOutcome qo WHERE qo.user.email = :email")
	QuizOutcome findByEmail(@Param("email") String email);

}
