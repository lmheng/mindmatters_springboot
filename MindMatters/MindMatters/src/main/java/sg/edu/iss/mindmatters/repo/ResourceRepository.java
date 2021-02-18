package sg.edu.iss.mindmatters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.mindmatters.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	
	Resource findByUrlCode(String urlcode);
	List<Resource>findByType(String type);

}
