package pl.coderslab.imageviewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.imageviewer.model.Role;
import pl.coderslab.imageviewer.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    public RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll(){
        return (List<Role>) roleRepository.findAll();
    };

    @Override
    public Optional<Role> findById(Integer id){return (Optional<Role>) roleRepository.findById(id); };
}
