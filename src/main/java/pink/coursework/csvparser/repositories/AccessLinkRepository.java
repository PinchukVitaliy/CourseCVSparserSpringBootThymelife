package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.AccessLink;

/**
 * Репозиторий класса ссылка
 * @Repository это постоянный уровень (Data Access Layer) приложения, который используется для получения данных из базы данных.
 * т.е. все связанные с базой данных операции выполняются хранилищем.
 */
@Repository
public interface AccessLinkRepository  extends JpaRepository<AccessLink, Integer> {
    /**<p>Поиск ссылки по самой ссылке</p>
     * @param link ссылка
     * @return обьект ссылка
     */
    AccessLink findByLink(String link);
}

