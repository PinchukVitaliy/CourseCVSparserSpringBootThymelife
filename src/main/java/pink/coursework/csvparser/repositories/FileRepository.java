package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Myfile;

@Repository
public interface FileRepository extends JpaRepository<Myfile, Integer> {
}
