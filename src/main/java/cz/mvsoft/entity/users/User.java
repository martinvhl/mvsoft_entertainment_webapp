package cz.mvsoft.entity.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_name")
	@Size(min = 2, max = 50, message = "Your name needs to be at least 2 and no more than 50 characters long.")
	@NotNull
	private String shownName;
	
	@NotNull
	@Email(message = "Please enter valid e-mail address!")
	@Max(value = 60, message = "Your e-mail address can't be longer than 60 characters.")
	@Column(name = "email")
	private String email;
	
	@Column(name = "login")
	@NotNull
	@Size(min = 8, max = 30, message = "Your login needs to be at least 8 and no more than 30 characters long.")
	private String login;
	
	@NotNull
	@Column(name="password")
	@Min(value = 8, message = "Your password should be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\n)[A-Za-z\n]{8,}$", message = "Your password should contain at least one number!")
	private String password;
	
}
