package cz.mvsoft.entity.entertainment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "is required.")
	private int id;
	
	@NotNull(message = "is required.")
	@Column(name = "title")
	@Size(max = 100, message = "Length of the title can't be longer than 100 characters.")
	private String title;
	
	@Column(name = "year")
	@Min(value = 1890, message = "Year of origin cannot be before 1890.")
	@Max(value = 2100, message = "Year of origin cannot be after 2100.")
	private int year;
	
	@Column(name = "description")
	@Size(max = 1600, message = "Description cannot be longer than 1600 characters.")
	private String description;
	
	@Lob
	@Column(name = "cover", columnDefinition = "mediumblob")
	private byte[] image;
	
	@NotNull(message = "is required.")
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDateTime;
	
	@NotNull(message = "is required.")
	@UpdateTimestamp
	@Column(name = "last_changed_date")
	private LocalDateTime lastModifiedDateTime;
}
