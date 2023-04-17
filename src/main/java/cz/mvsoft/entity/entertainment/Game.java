package cz.mvsoft.entity.entertainment;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
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
