package com.diplom.notificator.googleCV;

import com.google.cloud.vision.spi.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleCVTagsAnalizer {

    private ImageAnnotatorClient client;

    public GoogleCVTagsAnalizer(){
        try {
            this.client = ImageAnnotatorClient.create();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> detectLabels(Image img) {
        if (img == null){
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
        requests.add(request);

        BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = response.getResponsesList();

        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                System.out.printf("Error: %s\n", res.getError().getMessage());
                return null;
            }

            for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                result.add(annotation.getDescription());
            }
        }
        return result;
    }
}