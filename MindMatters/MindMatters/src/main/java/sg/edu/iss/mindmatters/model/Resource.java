package sg.edu.iss.mindmatters.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Resource implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String urlCode;
	private String description;
	private String name;
	private String type;
	
	public Resource(String urlCode, String description, String name,String type) {
		super();
		this.urlCode = urlCode;
		this.description = description;
		this.name = name;
		this.type=type;
	}
	
	
	
}
