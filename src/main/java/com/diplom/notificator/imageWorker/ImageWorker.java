package com.diplom.notificator.imageWorker;

import com.google.cloud.vision.v1.Image;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface ImageWorker {
    Image getImageFromPath(String filename);

    List<Image> getImageList(String directory);

}
