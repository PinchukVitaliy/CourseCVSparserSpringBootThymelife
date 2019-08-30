package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Statistic;

@Repository
public interface StatisticRepository  extends JpaRepository<Statistic, Integer> {
}
