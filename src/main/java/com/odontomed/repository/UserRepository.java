package com.odontomed.repository;

import com.odontomed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByDni(String dni);

    @Query(value = "SELECT * from usuario u where u.nombre = :firstname and u.apellido = :lastname", nativeQuery = true)
    User findByName(String firstname, String lastname);

    @Query(value = "UPDATE usuario u set u.tel = :tel where u.dni = :dni  ", nativeQuery = true)
    User updateUser(String dni, String tel);
}
