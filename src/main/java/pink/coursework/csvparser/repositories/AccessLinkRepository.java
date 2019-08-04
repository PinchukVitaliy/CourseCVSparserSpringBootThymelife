package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.AccessLink;

@Repository
public interface AccessLinkRepository  extends JpaRepository<AccessLink, Integer> {
    AccessLink findByLink(String link);
}

