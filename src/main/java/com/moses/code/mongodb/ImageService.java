package com.moses.code.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
@Service
public class ImageService {

    @Autowired
    private MongoDBImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final Logger logger = Logger.getLogger(ImageService.class.getName());

    public Image uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
                logger.info("Upload directory created at: " + uploadPath.toAbsolutePath());
            } catch (IOException e) {
                logger.severe("Failed to create upload directory: " + e.getMessage());
                throw new IOException("Could not create upload directory", e);
            }
        }

        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());
        logger.info("File uploaded successfully: " + filePath.toAbsolutePath());

        String imageUrl = "/images/" + uniqueFileName;

        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());

        logger.info("Image metadata saved successfully to MongoDB with URL: " + imageUrl);
        return imageRepository.save(image);
    }

    public List<Image> uploadImages(List<MultipartFile> files) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            images.add(uploadImage(file));
        }
        return images;
    }

}
