package core.september.rescue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.september.rescue.repo.ProfileRepo;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepo repo;

	public ProfileRepo getRepo() {
		return repo;
	}

	
}
