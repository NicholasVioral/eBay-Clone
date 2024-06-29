package edu.sru.cpsc.webshopping.domain.misc;

import edu.sru.cpsc.webshopping.domain.widgets.ImagePacket;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "image")
public class Image{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "image_id")
	private Long image_id;
	
	String imageName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_packet_packet_id")
	private ImagePacket imagePacket;

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}

	public ImagePacket getImagePacket() {
		return imagePacket;
	}

	public void setImagePacket(ImagePacket imagePacket) {
		this.imagePacket = imagePacket;
	}
	
	
}



