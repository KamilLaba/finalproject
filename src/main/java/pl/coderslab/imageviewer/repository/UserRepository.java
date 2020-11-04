package pl.coderslab.imageviewer.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.imageviewer.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
