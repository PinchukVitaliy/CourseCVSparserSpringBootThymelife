package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Myfile;

@Repository
public interface FileRepository extends JpaRepository<Myfile, Integer> {

    @Query("DELETE Myfile f WHERE f.creatorOfFile.id = ?1")
    @Modifying
    void deleteByCreatorOfFileId(Integer creatorOfFileId);

}
