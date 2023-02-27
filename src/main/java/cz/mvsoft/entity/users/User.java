package cz.mvsoft.entity.users;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "security_section",name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username")
	@Size(min = 2, max = 50, message = "Your username needs to be at least 2 and no more than 50 characters long.")
	@NotNull(message = "is required.")
	private String name;
	
	@NotNull(message = "is required.")
	@Column(name="password")
	@Min(value = 8, message = "Your password should be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\n)[A-Za-z\n]{8,}$", message = "Your password should contain at least one number!")
	private String password;
	
	@NotNull(message = "is required.")
	@Email(message = "Please enter valid e-mail address!")
	@Max(value = 60, message = "Your e-mail address can't be longer than 60 characters.")
	@Column(name = "email")
	private String email;
	
	@Column(name = "first_name")
	@NotNull(message = "is required.")
	@Size(min = 2, max = 50, message = "Your first name needs to be at least 2 and no more than 50 characters long.")
	private String firstName;
	
	@Column(name = "last_name")
	@NotNull(message = "is required.")
	@Size(max = 50, message = "Your last name needs to be at least 2 and no more than 50 characters long.")
	private String lastName;
	
	//private String roles; //mapujeme do tabulky user, ne do rolí, pro to máme příkald v některém z ostatních projektů!!! -> využijeme jen nastavení ve třídě SecurityConfiguration a opět upravíme podle sebe
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
}
