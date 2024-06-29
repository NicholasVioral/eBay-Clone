package edu.sru.cpsc.webshopping.controller.misc;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.misc.Image;
import edu.sru.cpsc.webshopping.repository.misc.ImageRepository;

@RestController
public class ImageController{
	private ImageRepository imageRepo;
	
	ImageController(ImageRepository imageRepo) {
		this.imageRepo = imageRepo;
	}
	
	/*
	 * gets entry of an image by id
	 * @param id id of image to be retrieved
	 * @return Image image associated with id
	 */
	@RequestMapping("/get-image-by-image-id/{image_id}")
	public Image getImageByImageId(@PathVariable("image_id") Long imageId) {
		return imageRepo.findById(imageId).orElseThrow(() -> new IllegalArgumentException("Invalid id passed to getImageByImageId."));
	}
	
	/*
	 * gets entry of an image by image name
	 * @param imageName	name of image to be retrieved
	 * @return Image image associated with image name
	 */
	@RequestMapping("/get-image-by-image-name/{image_name}")
	public Image getImageByImageName(@PathVariable("image_name") String imageName) {
		return imageRepo.findByImageName(imageName);
	}
	
	
	
}







