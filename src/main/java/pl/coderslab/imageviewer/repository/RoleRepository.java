package pl.coderslab.imageviewer.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.imageviewer.model.Role;
import pl.coderslab.imageviewer.model.User;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
