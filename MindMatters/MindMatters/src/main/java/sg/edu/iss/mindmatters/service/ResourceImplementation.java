package sg.edu.iss.mindmatters.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.mindmatters.model.Resource;
import sg.edu.iss.mindmatters.repo.ResourceRepository;

@Service
@Transactional
public class ResourceImplementation implements ResourceServices{
	
	@Autowired
	ResourceRepository rRepo;

	@Override
	public List<Resource> findAll() {
		return rRepo.findAll();
	}

	@Override
	public Resource findByUrlCode(String urlcode) {
		return rRepo.findByUrlCode(urlcode);
	}

	@Override
	public List<Resource> findByType(String type) {
		return rRepo.findByType(type);
	}
	

	public String findAllList()
	{
		String all="";
		List<Resource> res=rRepo.findAll();
		for(Resource r:res) {
			all=all+" "+ r;
		}
		return all;
	}
}
