package pl.coderslab.imageviewer.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.imageviewer.model.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {

}
