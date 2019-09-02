package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.AccessLink;
import pink.coursework.csvparser.models.Myfile;

/**
 * Репозиторий класса файл
 * @Repository это постоянный уровень (Data Access Layer) приложения, который используется для получения данных из базы данных.
 * т.е. все связанные с базой данных операции выполняются хранилищем.
 */
@Repository
public interface FileRepository extends JpaRepository<Myfile, Integer> {
    /**<p>Поиск файла по открытой ссылке</p>
     * @param access ссылка
     * @return файл
     */
    Myfile findByAccessLink(AccessLink access);
}
