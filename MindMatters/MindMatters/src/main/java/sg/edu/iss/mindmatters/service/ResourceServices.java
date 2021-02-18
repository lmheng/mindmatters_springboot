package sg.edu.iss.mindmatters.service;

import java.util.List;

import sg.edu.iss.mindmatters.model.Resource;

public interface ResourceServices {

	public List<Resource> findAll();
	public List<Resource> findByType(String type);
	public Resource findByUrlCode(String urlcode);
	public String findAllList();

	

}
