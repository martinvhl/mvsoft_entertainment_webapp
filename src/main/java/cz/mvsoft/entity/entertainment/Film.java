package cz.mvsoft.entity.entertainment;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(schema = "entertainment_section", name = "films")
@Data
@SuperBuilder //for test purposes (EXPERIMENTAL), alt add super() constructors to field based constructors in children classes
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Film extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id")
	private int id;
	
	@Size(max = 100, message = "Length of the subtitle can!t be longer than 100 characters.")
	@Column(name = "subtitle")
	private String subTitle;
	
	@NotBlank(message = "is required.")
	@Column(name = "director")
	@Size(max = 100, message = "Length of the director's name can't be longer than 100 characters.")
	private String director;
	
	@Positive
	@Max(value = 999, message = "Movie can't be longer than 999 minutes!")
	@Column(name = "length")
	private int length;
	
	@Column(name="film_type")
	@NotBlank
	private String filmType;
	
	@Transient
	private String actorsInput;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinTable(name = "actors_in_films", joinColumns = @JoinColumn(name = "film_id"),
			inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private List<Actor> actors;
}
