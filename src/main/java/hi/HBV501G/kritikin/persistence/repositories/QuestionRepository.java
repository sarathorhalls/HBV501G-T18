package hi.HBV501G.kritikin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question save(Question question);

    void delete(Question question);

    List<Question> findAll();

    Question findById(long id);

    List<Question> findByCompany(long companyId);

    List<Question> findByUser(long userId);
}
