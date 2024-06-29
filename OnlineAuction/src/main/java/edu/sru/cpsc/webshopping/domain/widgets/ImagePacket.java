package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.HashSet;
import java.util.Set;

import edu.sru.cpsc.webshopping.domain.misc.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "image_packet")
public class ImagePacket{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "packet_id")
	private Long id;

	@OneToMany(mappedBy = "imagePacket")
	private Set<Image> images = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void addImage(Image image) {
		this.images.add(image);
		image.setImagePacket(this);
	}

	public void removeImage(Image image) {
		this.images.remove(image);
		image.setImagePacket(null);
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}
}