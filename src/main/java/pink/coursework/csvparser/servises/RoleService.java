package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listRoles(){
        return roleRepository.findAll();
    }
    public void editRole(Role role){
        roleRepository.save(role);
    }
    public void createRole(Role role){
        roleRepository.save(role);
    }
    public Role getRole(Integer id){
        return roleRepository.getOne(id);
    }

}
