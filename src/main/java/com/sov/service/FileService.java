package com.sov.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileService {

    private static final ConcurrentHashMap<Long, String> CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, String> INV_CACHE = new ConcurrentHashMap<>();

    private static final String KEY = "***KEY***";
    private static final String SECRET = "***SECRET***";
    private static final String BUCKET_NAME = "***BUCKET***";
    private static final AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(KEY, SECRET)))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();

    public void saveProjectLogo(Long projectId, MultipartFile logo) {
        try {
            File tempFile = File.createTempFile("project_logo_" + projectId, ".jpg");
            Files.write(tempFile.toPath(), logo.getBytes());
            s3client.putObject(BUCKET_NAME, "logos/dev/" + projectId + ".jpg", tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInvestorPhoto(Long id, MultipartFile logo) {
        try {
            File tempFile = File.createTempFile("photo_" + id, ".jpg");
            Files.write(tempFile.toPath(), logo.getBytes());
            s3client.putObject(BUCKET_NAME, "photos/dev/" + id + ".jpg", tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProjectLogo(Long projectId) {
        try {
            String cacheValue = CACHE.get(projectId);
            if (cacheValue != null) {
                return cacheValue;
            }
            String logoUrl = s3client.getObject(BUCKET_NAME, "logos/dev/" + projectId + ".jpg")
                    .getObjectContent().getHttpRequest().getURI().toString();
            CACHE.put(projectId, logoUrl);
            return logoUrl;
        } catch (Exception e) {
            return null;
        }
    }

    public String getInvestorPhoto(Long id) {
        try {
            String cacheValue = INV_CACHE.get(id);
            if (cacheValue != null) {
                return cacheValue;
            }
            String logoUrl = s3client.getObject(BUCKET_NAME, "photos/dev/" + id + ".jpg")
                    .getObjectContent().getHttpRequest().getURI().toString();
            INV_CACHE.put(id, logoUrl);
            return logoUrl;
        } catch (Exception e) {
            return null;
        }
    }
}
