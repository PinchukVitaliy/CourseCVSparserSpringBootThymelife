package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.User;

import java.util.List;

/**
 * Репозиторий класса пользователь
 * @Repository это постоянный уровень (Data Access Layer) приложения, который используется для получения данных из базы данных.
 *  т.е. все связанные с базой данных операции выполняются хранилищем.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**<p>Поиск пользователя по почте</p>
     * @param email почта
     * @return пользователь
     */
    User findByEmail(String email);

    /**<p>Поиск пользователя по коду активации</p>
     * @param code код
     * @return пользователь
     */
    User findByActivationCode(String code);
}
