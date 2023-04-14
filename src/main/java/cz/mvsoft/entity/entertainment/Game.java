package cz.mvsoft.entity.entertainment;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(schema = "entertainment_section", name = "games")
@Data
@SuperBuilder //for test purposes (EXPERIMENTAL), alt add super() constructors to field based constructors in children classes
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Game extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_id")
	private int id;
	
	@Column(name = "game_type")
	@NotBlank
	private String gameType;
	
	@Transient
	@NotBlank
	private String typedStudio;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "developer")
	private GameStudio developer;
}
