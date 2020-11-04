package pl.coderslab.imageviewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.imageviewer.model.Image;
import pl.coderslab.imageviewer.model.Role;
import pl.coderslab.imageviewer.model.User;
import pl.coderslab.imageviewer.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    public UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll(){
        return (List<User>)userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id){return (Optional<User>) userRepository.findById(id); };

    @Override
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
}
