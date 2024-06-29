package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.ImagePacket;
import edu.sru.cpsc.webshopping.repository.widgets.ImagePacketRepository;

@RestController
public class ImagePacketController{
	private ImagePacketRepository packetRepo;
	
	ImagePacketController(ImagePacketRepository packetRepo) {
		this.packetRepo = packetRepo;
	}
	
	/*
	 * gets entry of an image packet by id
	 * @param id id of image packet to be retrieved
	 * @return ImagePacket image packet associated with id
	 */
	@RequestMapping("/get-image-packet-by-id/{id}")
	public ImagePacket getImagePacketById(@PathVariable("id") Long id) {
		return packetRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id passed to getImagePacketById."));
	}
	
	
	
	
}







