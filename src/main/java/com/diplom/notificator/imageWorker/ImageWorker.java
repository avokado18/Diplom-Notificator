package com.diplom.notificator.imageWorker;

import com.google.cloud.vision.v1.Image;

import java.io.IOException;
import java.util.List;

public interface ImageWorker {
    Image getImageFromPath(String filename);

    List<Image> getImageList(String directory);

}
