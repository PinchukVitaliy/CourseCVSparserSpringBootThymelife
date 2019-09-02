package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Role;

/**
 * Репозиторий класса роль
 * @Repository это постоянный уровень (Data Access Layer) приложения, который используется для получения данных из базы данных.
 *  т.е. все связанные с базой данных операции выполняются хранилищем.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**<p>Поиск роли по имени</p>
     * @param name имя роли
     * @return роль
     */
    Role findByName(String name);
}
