package com.example.assignment1.service;
import java.io.IOException;
import java.util.*;

import com.amazonaws.services.s3.AmazonS3;
import com.example.assignment1.Exception.BadInputException;
import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.entity.Image;
import com.example.assignment1.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AmazonS3 amazonS3;

    public Image saveImage(Long productId, Long userId, MultipartFile file) {
        // check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        System.out.println("30");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        // Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s/%s", bucketName, userId, productId);
        String fileName = String.format("%s/%s",String.valueOf(UUID.randomUUID()), file.getOriginalFilename());
        System.out.println(path+" "+fileName);
        try {
            fileStore.uploadImage(path, fileName, Optional.of(metadata), file.getInputStream());
//			System.out.println(amazonS3.getUrl(path, fileName));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Image image = Image.builder().productId(productId).fileName(fileName).s3BucketPath(String.valueOf(amazonS3.getUrl(path, fileName))).build();
        return imageRepository.saveAndFlush(image);
    }

    public List<Image> getAllImages(Long productId, Long userId) throws DataNotFoundException {
        // TODO Auto-generated method stub
        List<Image> images=imageRepository.findImageByProductId(productId);
        if(images==null)
            throw new DataNotFoundException("No Product with given Id");
        return images;
    }

    public Image getImage(Long productId, Long userId, Long imageId) throws DataNotFoundException, BadInputException {
        // TODO Auto-generated method stub
        Optional<Image> imgObj=imageRepository.findById(imageId);
        if(imgObj.isEmpty())
            throw new DataNotFoundException("No Image with given Id");
        System.out.println(imgObj.get().getProductId()+" "+productId);
        if(imgObj.get().getProductId()!=productId)
            throw new BadInputException("ProductId and imageId won't match");
        return imgObj.get();
    }

    public Image deleteImage(Long productId, Long userId, Long imageId)  throws DataNotFoundException, BadInputException {
        // TODO Auto-generated method stub
        Optional<Image> imgObj=imageRepository.findById(imageId);
        if(imgObj.isEmpty())
            throw new DataNotFoundException("No Image with given Id");
        System.out.println(imgObj.get().getProductId()+" "+productId);
        if(imgObj.get().getProductId()!=productId)
            throw new BadInputException("ProductId and imageId won't match");
        System.out.println(imgObj.get().getS3BucketPath()+" "+imgObj.get().getFileName());
        String path = String.format("%s/%s/%s", bucketName, userId, productId);
        fileStore.deleteFile(path,imgObj.get().getFileName());
        return imgObj.get();
    }

    public void deleteImageByProductId(Long productId, Long userId)  throws DataNotFoundException {
        // TODO Auto-generated method stub
        String path = String.format("%s/%s/%s", bucketName, userId, productId);
        fileStore.deleteFile(path,"/");
    }

}
