package sg.edu.iss.mindmatters.model;


public class Quiz {

	private int[] ques;

	public Quiz() {
		super();
		ques = new int[24];
	}

	public Quiz(int[] ques) {
		this.ques=ques;
	}

	public int[] getQues() {
		return ques;
	}

	public void setQues(int[] ques) {
		this.ques = ques;
	}

	public int getQuestion(int index) {
		return ques[index];
	}

}
