package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.RoleRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Role> listRoles(){
        return roleRepository.findAll();
    }
    public void editRole(Role role, List<User> IdsToAdd, List<User>  IdsToDelete){
        if(IdsToAdd != null)
        {
            for(int i = 0; i < IdsToAdd.size(); i++){
                IdsToAdd.get(i).getRoleList().add(role);
                userRepository.save(IdsToAdd.get(i));
            }
        }
        if(IdsToDelete != null)
        {
            for(int j = 0; j < IdsToDelete.size(); j++){
                IdsToDelete.get(j).getRoleList().remove(role);
                userRepository.save(IdsToDelete.get(j));
            }
        }
    }
    public void createRole(Role role){
        roleRepository.save(role);
    }
    public Role getRole(Integer id){
        return roleRepository.getOne(id);
    }
    public List<User> listUsersNoRole(Integer idRole){
        List<User> listUsers = userRepository.findAll();
        List<User> listUsersNoRole = new ArrayList<>();
        Role role = roleRepository.getOne(idRole);
        for(int i = 0; i < listUsers.size(); i++){
               boolean flag = listUsers.get(i).getRoleList().contains(role);
               if(!flag){
                   listUsersNoRole.add(listUsers.get(i));
               }
        }
        return listUsersNoRole;
    }
    public void deleteRole(Role role){
        roleRepository.delete(role);
    }
}
