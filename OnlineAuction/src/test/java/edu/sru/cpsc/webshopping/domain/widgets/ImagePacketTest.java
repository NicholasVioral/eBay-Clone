package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.sru.cpsc.webshopping.domain.misc.Image;

public class ImagePacketTest {

    private ImagePacket imagePacket;
    private Set<Image> images;

    @BeforeEach
    public void setUp() {
        imagePacket = new ImagePacket();
        images = new HashSet<>();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        imagePacket.setId(id);
        assertEquals(id, imagePacket.getId());
    }

    @Test
    public void testGetImages() {
        Image image1 = new Image();
        Image image2 = new Image();
        images.add(image1);
        images.add(image2);
        imagePacket.setImages(images);
        assertEquals(images, imagePacket.getImages());
    }

    @Test
    public void testAddImage() {
        Image image = new Image();
        imagePacket.addImage(image);
        assertNotNull(imagePacket.getImages());
        assertEquals(1, imagePacket.getImages().size());
    }

    @Test
    public void testRemoveImage() {
        Image image = new Image();
        imagePacket.addImage(image);
        imagePacket.removeImage(image);
        assertNotNull(imagePacket.getImages());
        assertEquals(0, imagePacket.getImages().size());
    }
}