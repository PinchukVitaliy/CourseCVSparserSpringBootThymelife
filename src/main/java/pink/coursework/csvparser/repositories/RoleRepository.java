package pink.coursework.csvparser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.models.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
