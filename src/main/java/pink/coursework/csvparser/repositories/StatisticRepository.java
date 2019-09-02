package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Statistic;

/**
 * Репозиторий класса статистика
 * @Repository это постоянный уровень (Data Access Layer) приложения, который используется для получения данных из базы данных.
 *  т.е. все связанные с базой данных операции выполняются хранилищем.
 */
@Repository
public interface StatisticRepository  extends JpaRepository<Statistic, Integer> {
}
