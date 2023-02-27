package cz.mvsoft.entity.products;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import cz.mvsoft.entity.users.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "films")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Film extends BaseEntity {
	
	@Size(max = 100, message = "Length of the subtitle can!t be longer than 100 characters.")
	@Column(name = "subtitle")
	private String subTitle;
	
	@NotNull(message = "is required.")
	@Column(name = "director")
	@Size(max = 100, message = "Length of the director's name can't be longer than 100 characters.")
	private String director;
	
	@Positive
	@Max(value = 999, message = "Movie can't be longer than 999 minutes!")
	@Column(name = "length")
	private int length;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinTable(name = "users_films", joinColumns = @JoinColumn(name = "film_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinTable(name = "actors_in_films", joinColumns = @JoinColumn(name = "film_id"),
			inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private List<Actor> actors;
}
