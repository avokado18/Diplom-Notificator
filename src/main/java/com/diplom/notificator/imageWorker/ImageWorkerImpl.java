package com.diplom.notificator.imageWorker;

import ch.qos.logback.core.util.FileUtil;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageWorkerImpl implements ImageWorker{
    @Override
    public Image getImageFromPath(String fileName) {
        try {
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);
            Image img = Image.newBuilder().setContent(imgBytes).build();
            return img;
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Image> getImageList(String directory) {
        File myFolder = new File(directory);
        File[] files = myFolder.listFiles();
        List<Image> images = new ArrayList<>();
        if (files != null && files.length != 0){
            for (File file : files) {
                Image image = getImageFromPath(file.toString());
//                file.delete();
                if (image != null) {
                    images.add(image);
                }
            }
            return images;
        }
        return new ArrayList<>();
    }


}
