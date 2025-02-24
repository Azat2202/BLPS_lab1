package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
