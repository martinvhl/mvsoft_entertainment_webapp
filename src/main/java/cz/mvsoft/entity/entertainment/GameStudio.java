package cz.mvsoft.entity.entertainment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
