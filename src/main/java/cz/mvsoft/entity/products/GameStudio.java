package cz.mvsoft.entity.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "studios")
@Data
@Builder
public class GameStudio {
	
	@Id
	@Column(name = "studio_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	@NotNull
	@Size(max = 50, message = "Studio's name can't be longer than 50 characters.")
	private String name;
	
	@Column(name = "country")
	@Size(max = 20, message = "Studio's country of origin's name can't be longer than 20 characters.")
	private String countryOfOrigin;
}
