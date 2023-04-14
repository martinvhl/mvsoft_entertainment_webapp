package cz.mvsoft.entity.entertainment;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
