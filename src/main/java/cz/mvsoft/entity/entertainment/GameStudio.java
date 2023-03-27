package cz.mvsoft.entity.entertainment;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "entertainment_section", name = "studios")
@Data
@NoArgsConstructor
public class GameStudio {
	
	@Id
	@Column(name = "studio_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	@NotBlank(message = "is required.")
	@Size(max = 50, message = "Studio's name can't be longer than 50 characters.")
	private String name;

	public GameStudio(
			@NotBlank(message = "is required.") @Size(max = 50, message = "Studio's name can't be longer than 50 characters.") String name) {
		this.name = name;
	}
}
