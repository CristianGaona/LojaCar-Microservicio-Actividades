package lojacar.ms.task.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="activity")
public class Activity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String description;
	@Column(name = "employee_id")
	private Integer employeeId;
	
	@Column(name = "client_id")
	private Integer clientId;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_assignment")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date  dateAssignment;
	
	/*@PrePersist
	public void prePersist() {
		dateAssignment = new Date();
	}*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDateAssignment() {
		return dateAssignment;
	}

	public void setDateAssignment(Date dateAssignment) {
		this.dateAssignment = dateAssignment;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	
	
}
