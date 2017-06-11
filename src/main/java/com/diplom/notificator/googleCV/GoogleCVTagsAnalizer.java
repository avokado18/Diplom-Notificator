package com.diplom.notificator.googleCV;

import com.google.cloud.vision.spi.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class GoogleCVTagsAnalizer {

    private ImageAnnotatorClient client;

    public GoogleCVTagsAnalizer(){
        try {
            this.client = ImageAnnotatorClient.create();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Map<Integer, Set<String>> detectLabels(List<Image> imgs) {
        if (imgs == null || imgs.isEmpty()){
            return new HashMap<>();
        }
        Map<Integer, Set<String>> imageTags = new HashMap<>();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        for (Image img : imgs){
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            requests.add(request);
        }

        BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = response.getResponsesList();

        for (int i = 0; i < responses.size(); i++) {
            Set<String> tags = new HashSet<>();
            if (responses.get(i).hasError()) {
                System.out.printf("Error: %s\n", responses.get(i).getError().getMessage());
                return null;
            }

            for (EntityAnnotation annotation : responses.get(i).getLabelAnnotationsList()) {
                tags.add(annotation.getDescription());
            }
            imageTags.put(i, tags);
        }
        return imageTags;
    }
}
