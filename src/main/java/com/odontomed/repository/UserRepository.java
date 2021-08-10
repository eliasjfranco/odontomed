package com.odontomed.repository;

import com.odontomed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD

    User findByEmail(String email);
=======
>>>>>>> 3094fd949cc2467fe38d888808499cfd1e4e5b53
}
