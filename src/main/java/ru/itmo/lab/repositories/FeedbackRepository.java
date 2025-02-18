package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
