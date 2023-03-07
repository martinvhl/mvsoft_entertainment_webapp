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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "security_section",name = "user")
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username")
	@Size(min = 2, max = 50, message = "Your username needs to be at least 2 and no more than 50 characters long.")
	@NotBlank(message = "is required.")
	private String userName;
	
	@NotBlank(message = "is required.")
	@Column(name="password")
	@Size(min = 8, message = "Your password should be at least 8 characters long")
	private String password;
	
	@NotBlank(message = "is required.")
	@Email(message = "Please enter valid e-mail address!")
	@Size(max = 60, message = "Your e-mail address can't be longer than 60 characters.")
	@Column(name = "email")
	private String email;
	
	@Column(name = "first_name")
	@NotBlank(message = "is required.")
	@Size(min = 2, max = 50, message = "Your first name needs to be at least 2 and no more than 50 characters long.")
	private String firstName;
	
	@Column(name = "last_name")
	@NotBlank(message = "is required.")
	@Size(min = 2, max = 50, message = "Your last name needs to be at least 2 and no more than 50 characters long.")
	private String lastName;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
}
