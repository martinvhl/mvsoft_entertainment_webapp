package cz.mvsoft.entity.entertainment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(schema = "entertainment_section", name = "actors")
@Data
public class Actor {

	@Id
	@Column(name = "actor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	@NotBlank(message = "is required.")
	@Size(max = 100, message = "Actor's name can't be longer than 100 characters.")
	private String name;

	public Actor(
			@NotBlank(message = "is required.") @Size(max = 100, message = "Actor's name can't be longer than 100 characters.") String name) {
		this.name = name;
	}
	
	
}
