package sg.edu.iss.mindmatters.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class QuizOutcome implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private LocalDate nextQuiz;
	private String quizOutcome;
	
	@JsonIgnore
	@OneToOne
	private User user;

	public QuizOutcome() {
		super();
	}

	public QuizOutcome(LocalDate nextQuiz, String quizOutcome, User user) {
		this.nextQuiz = nextQuiz;
		this.quizOutcome = quizOutcome;
		this.user = user;
	}

	public LocalDate getNextQuiz() {
		return nextQuiz;
	}

	public void setNextQuiz(LocalDate nextQuiz) {
		this.nextQuiz = nextQuiz;
	}

	public String getQuizOutcome() {
		return quizOutcome;
	}

	public void setQuizOutcome(String quizOutcome) {
		this.quizOutcome = quizOutcome;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "QuizOutcome [id=" + id + ", nextQuiz=" + nextQuiz + ", quizOutcome=" + quizOutcome 
				+ "]";
	}
	
}
