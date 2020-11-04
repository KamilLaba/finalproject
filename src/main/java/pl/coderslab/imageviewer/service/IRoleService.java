package pl.coderslab.imageviewer.service;


import pl.coderslab.imageviewer.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> findAll();
    Optional<Role> findById(Integer id);
}
