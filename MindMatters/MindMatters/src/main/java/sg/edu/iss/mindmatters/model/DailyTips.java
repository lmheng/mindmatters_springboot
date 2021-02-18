package sg.edu.iss.mindmatters.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DailyTips implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String quotes;
	
	public DailyTips(Long id, String quotes) {
		super();
		this.id = id;
		this.quotes = quotes;
	}
	
	
}
