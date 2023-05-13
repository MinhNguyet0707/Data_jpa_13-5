package CreateUser1.demo.service;


import CreateUser1.demo.entity.User;
import CreateUser1.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllListUser() {
        List<User> listUser = userRepository.findAll();
        listUser.stream().forEach(user -> {
            user.setAvatar("http://localhost:8080/images" + "/" + user.getAvatar());
        });
        return listUser;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        Optional<User> optional = userRepository.findById((long) id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" User not found for id :: " + id);
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Optional<User> searchUserByName(String name) {
        return userRepository.findByName(name);
    }
}
