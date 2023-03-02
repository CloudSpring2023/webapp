package com.example.assignment1.repository;

import com.example.assignment1.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value="SELECT * FROM IMAGE WHERE product_id =?",
            nativeQuery = true)
    List<Image> findImageByProductId(Long productId);
}
