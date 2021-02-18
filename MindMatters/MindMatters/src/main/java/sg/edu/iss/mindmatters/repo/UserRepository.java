package sg.edu.iss.mindmatters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.mindmatters.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);

	User findByEmail(String email);

}
