package edu.sru.cpsc.webshopping.domain.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.widgets.ImagePacket;

class ImageTest {
    private Image image;
    private ImagePacket imagePacket;

    @BeforeEach
    void setUp() {
        image = new Image();
        imagePacket = new ImagePacket(); // Assuming ImagePacket has a no-args constructor
    }

    @Test
    void testGetAndSetImageId() {
        assertNull(image.getImage_id(), "Initially, image_id should be null");
        Long imageId = 1L;
        image.setImage_id(imageId);
        assertEquals(imageId, image.getImage_id(), "Image ID should match the set value");
    }

    @Test
    void testGetAndSetImagePacket() {
        assertNull(image.getImagePacket(), "Initially, imagePacket should be null");
        image.setImagePacket(imagePacket);
        assertEquals(imagePacket, image.getImagePacket(), "ImagePacket should match the set ImagePacket");
    }
}
