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
@Table(schema = "entertainment_section", name = "actors")
@Data
@NoArgsConstructor
public class Actor {

	@Id
	@Column(name = "actor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", unique = true)
	@NotBlank(message = "is required.")
	@Size(max = 100, message = "Actor's name can't be longer than 100 characters.")
	private String name;

	public Actor(
			@NotBlank(message = "is required.") @Size(max = 100, message = "Actor's name can't be longer than 100 characters.") String name) {
		this.name = name;
	}
	
	
}
