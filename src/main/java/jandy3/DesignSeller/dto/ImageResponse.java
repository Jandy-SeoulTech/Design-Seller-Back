package jandy3.DesignSeller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ImageResponse {
    private List<String> images;
    public ImageResponse() {
        this.images = new ArrayList<>();
    }
}
