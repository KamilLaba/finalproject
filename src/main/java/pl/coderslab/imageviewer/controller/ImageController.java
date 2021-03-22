package pl.coderslab.imageviewer.controller;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.imageviewer.model.Image;
import pl.coderslab.imageviewer.service.IImageService;
import pl.coderslab.imageviewer.service.ImageService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageController {
    private IImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public String findImage(HttpSession session, Model model){
        if(session.getAttribute("userName") != null && !session.getAttribute("userName").toString().isEmpty()) {
            // add `message` attribute
            model.addAttribute("message", "Current user: " + session.getAttribute("userName").toString());
        } else {
            model.addAttribute("message", "No user selected");
        }

        List<Image> images = new ArrayList<>();
        if(session.getAttribute("roleId") != null && !session.getAttribute("roleId").toString().isEmpty()){
            int userRoleId = (Integer) session.getAttribute("roleId");
            images = (List<Image>)imageService.findAll().stream().filter(image -> image.getRole().getId() == userRoleId).collect(Collectors.toList());
        }

        model.addAttribute("images", images);

        return "images";
    }

    @PostMapping("images/deleteImage")
    public String deleteImage(@RequestParam String id){
        imageService.deleteById(Integer.parseInt(id));
        return "redirect:/images";
    }

    @PostMapping("images/toGrayscale")
    public String toGrayscale(@RequestParam String id){
        try {
            Image image = imageService.findById(Integer.parseInt(id));
            String imageBinaryString = image.getImage();

            byte[] inputImage = Base64.getDecoder().decode(imageBinaryString);
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputImage);
            BufferedImage inputBufferedImage = ImageIO.read(byteArrayInputStream);

            Mat source = bufferedImageToMat(inputBufferedImage);
            Mat destination = new Mat(inputBufferedImage.getHeight(), inputBufferedImage.getWidth(), inputBufferedImage.getType());
            Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);

            byte[] data1 = new byte[destination.rows() * destination.cols() * (int) (destination.elemSize())];
            destination.get(0, 0, data1);
            BufferedImage outputImage = new BufferedImage(destination.cols(), destination.rows(), BufferedImage.TYPE_BYTE_GRAY);
            outputImage.getRaster().setDataElements(0, 0, destination.cols(), destination.rows(), data1);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "png", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            image.setImage(bytes);
            imageService.save(image);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/images";
    }

    @PostMapping("images/toBlackAndWhite")
    public String toBlackAndWhite(@RequestParam String id){
        try {
            Image image = imageService.findById(Integer.parseInt(id));
            String imageBinaryString = image.getImage();

            byte[] inputImage = Base64.getDecoder().decode(imageBinaryString);
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputImage);
            BufferedImage inputBufferedImage = ImageIO.read(byteArrayInputStream);

            Mat source = bufferedImageToMat(inputBufferedImage);
            Mat destination = new Mat(inputBufferedImage.getHeight(), inputBufferedImage.getWidth(), inputBufferedImage.getType());
            Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);
            //(thresh, blackAndWhiteImage) = cv2.threshold(grayImage, 127, 255, cv2.THRESH_BINARY)
            byte[] data1 = new byte[destination.rows() * destination.cols() * (int) (destination.elemSize())];
            destination.get(0, 0, data1);
            BufferedImage outputImage = new BufferedImage(destination.cols(), destination.rows(), BufferedImage.TYPE_BYTE_GRAY);
            outputImage.getRaster().setDataElements(0, 0, destination.cols(), destination.rows(), data1);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "png", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            image.setImage(bytes);
            imageService.save(image);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/images";
    }

    protected Mat bufferedImageToMat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;
        System.out.println(in.getType());
        if (in.getType() == BufferedImage.TYPE_INT_RGB || in.getType() == BufferedImage.TYPE_BYTE_INDEXED || in.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
            data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
            for (int i = 0; i < dataBuff.length; i++) {
                data[i * 3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i * 3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
            }
        } else {
            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC1);
            data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
            for (int i = 0; i < dataBuff.length; i++) {
                r = (byte) ((dataBuff[i] >> 0) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
            }
        }
        out.put(0, 0, data);
        return out;
    }
}