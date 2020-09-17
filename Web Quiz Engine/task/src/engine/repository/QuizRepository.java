package engine.repository;

import engine.model.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@Repository
public interface QuizRepository extends PagingAndSortingRepository<Question, Long> {

    @Override
    Optional<Question> findById(Long id);

    @Override
    <Q extends Question> Q save(Q question);
}
