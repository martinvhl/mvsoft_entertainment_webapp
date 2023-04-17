package cz.mvsoft.entity.entertainment;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder //for test purposes (EXPERIMENTAL), alt add super() constructors to field based constructors in children classes
@NoArgsConstructor
public class BaseEntity {
	
	@NotBlank(message = "is required.")
	@Column(name = "title")
	@Size(max = 100, message = "Length of the title can't be longer than 100 characters.")
	private String title;
	
	@Column(name = "year")
	@Min(value = 1890, message = "Year of origin cannot be before 1890.")
	@Max(value = 2100, message = "Year of origin cannot be after 2100.")
	private int year;
	
	@NotBlank
	@Column(name = "description")
	@Size(max = 1600, message = "Description cannot be longer than 1600 characters.")
	private String description;
	
	@Lob
	@Column(name = "cover")
	@Size(max = 5242880, message = "Image size must be less than 5MB")
	private byte[] image;
	
	@Transient
	private String base64Encoded;
	
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDateTime;
	
	@UpdateTimestamp
	@Column(name = "last_changed_date")
	private LocalDateTime lastModifiedDateTime;
}
