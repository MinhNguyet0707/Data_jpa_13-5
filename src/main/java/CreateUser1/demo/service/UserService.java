package CreateUser1.demo.service;



import CreateUser1.demo.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
     List<User> getAllListUser();
     void saveUser(User user);
     User getUserById(int id);

     void deleteUser(long id);
     Optional<User> searchUserByName(String name);
}