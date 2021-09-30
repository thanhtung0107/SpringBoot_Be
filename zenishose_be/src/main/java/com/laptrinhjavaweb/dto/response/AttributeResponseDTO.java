package com.laptrinhjavaweb.dto.response;

import com.laptrinhjavaweb.dto.AbstractDTO;

public class AttributeResponseDTO extends AbstractDTO {

    private String size;
    private String color;
    private String[] images = new String[]{};

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
