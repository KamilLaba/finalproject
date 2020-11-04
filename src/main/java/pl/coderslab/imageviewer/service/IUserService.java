package pl.coderslab.imageviewer.service;


import pl.coderslab.imageviewer.model.Image;
import pl.coderslab.imageviewer.model.Role;
import pl.coderslab.imageviewer.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();
    User save(User user);
    Optional<User> findById(Integer id);
    void deleteById(Integer id);
}
