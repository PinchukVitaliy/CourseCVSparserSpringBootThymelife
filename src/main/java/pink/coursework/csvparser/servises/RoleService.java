package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.RoleRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис работы с ролями
 * @Service содержат бизнес-логику и вызывают методы на уровне хранилища.
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 */
@Service
public class RoleService {
    //Экземпляр репозитория роли
    @Autowired
    private RoleRepository roleRepository;
    //Экземпляр репозитория юзер
    @Autowired
    private UserRepository userRepository;

    /**<p>Все роли</p>
     * <p>Возращает все роли</p>
     * @return список ролей
     */
    public List<Role> listRoles(){
        return roleRepository.findAll();
    }

    /**<p>Умтановка ролей юзерам</p>
     * @param role  обьект роли
     * @param IdsToAdd список пользователей получающий роль
     * @param IdsToDelete список пользователей теряющих роль
     */
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

    /**<p>Создание новой роли</p>
     * @param role новый объект роли
     */
    public void createRole(Role role){
        roleRepository.save(role);
    }

    /**<p>Получения объекта роли по идентификатору</p>
     * @param id индетификатор
     * @return объект роли
     */
    public Role getRole(Integer id){
        return roleRepository.getOne(id);
    }

    /**<p>Список пользователей не имеющие определенную роль</p>
     * @param idRole идентификатор роли
     * @return список пользователей
     */
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

    /**<p>Удаление роли</p>
     * @param role обьект класса роль
     */
    public void deleteRole(Role role){
        roleRepository.delete(role);
    }

    /**<p>Поиск пользователей по ролям</p>
     * @param search фильтр поиска
     * @param resultList список пользователей имеющих определенную роль
     * @return список пользователей
     */
    public List<User> searchList(String search, List<User> resultList)
    {
        List<User> searchList = null;
        if(search.isEmpty()){
            return searchList;
        }
        if (!search.isEmpty()){
            List<User> userList = resultList;
            searchList = new ArrayList<User>();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getEmail().toLowerCase().contains(search.toLowerCase())) {
                    searchList.add(userList.get(i));
                }
            }
        }
        return searchList;
    }
}
