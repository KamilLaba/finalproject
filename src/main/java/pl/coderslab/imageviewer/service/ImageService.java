package pl.coderslab.imageviewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.imageviewer.model.Image;
import pl.coderslab.imageviewer.repository.ImageRepository;

import java.util.List;

@Service
public class ImageService implements IImageService{
    public ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> findAll(){
        return (List<Image>)imageRepository.findAll();
    }

    @Override
    public Image save(Image image){
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(Integer id){
        imageRepository.deleteById(id);
    }
}
