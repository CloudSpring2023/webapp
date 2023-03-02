package com.example.assignment1.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id",nullable = false)
    @JsonProperty(value="image_id" ,access = JsonProperty.Access.READ_ONLY)
    private Long imageId;

    @Column(name="product_id",nullable = false)
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("file_name")
    @Column(name="file_name",nullable = false)
    private String fileName;

    @JsonProperty(value ="date_created",access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_created")
    private LocalDateTime dateCreated;


    @JsonProperty(value="s3_bucket_path",access = JsonProperty.Access.READ_ONLY)
    @Column(name="s3_bucket_path",nullable = false)
    private String s3BucketPath;
}
