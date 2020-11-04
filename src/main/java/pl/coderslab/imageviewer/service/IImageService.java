package pl.coderslab.imageviewer.service;


import pl.coderslab.imageviewer.model.Image;

import java.util.List;

public interface IImageService {
    List<Image> findAll();
    Image save(Image image);
    void deleteById(Integer id);
}
