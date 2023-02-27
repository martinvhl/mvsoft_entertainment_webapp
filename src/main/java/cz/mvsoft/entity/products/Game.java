package cz.mvsoft.entity.products;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cz.mvsoft.entity.users.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "games")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class Game extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private GameStudio developer;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}) //pozor na cascade - je možné, že to nebude ALL!
	@JoinTable(name = "users_games", joinColumns = @JoinColumn(name = "game_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
}
