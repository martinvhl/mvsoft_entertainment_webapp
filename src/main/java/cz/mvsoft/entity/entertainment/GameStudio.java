package cz.mvsoft.entity.entertainment;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(schema = "entertainment_section", name = "studios")
@Data
@Builder
public class GameStudio {
	
	@Id
	@Column(name = "studio_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	@NotBlank(message = "is required.")
	@Size(max = 50, message = "Studio's name can't be longer than 50 characters.")
	private String name;
	
	@Column(name = "country")
	@Size(max = 20, message = "Studio's country of origin's name can't be longer than 20 characters.")
	private String countryOfOrigin;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "developer")
	private Set<Game> games;
}
