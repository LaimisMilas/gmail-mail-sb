package lt.gmail.mail.sender.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "phone_number")
public class PhoneNumberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String number;
	private String operator;
	@Column(name = "primary_number")
	private boolean primaryNumber;
	@OneToMany(targetEntity = PhoneNumberEntity.class)
	private List<PhoneNumberHistoryEntity> numberHistory = new ArrayList<PhoneNumberHistoryEntity>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<PhoneNumberHistoryEntity> getNumberHistory() {
		return numberHistory;
	}

	public void setNumberHistory(List<PhoneNumberHistoryEntity> numberHistory) {
		this.numberHistory = numberHistory;
	}

	public boolean isPrimaryNumber() {
		return primaryNumber;
	}

	public void setPrimaryNumber(boolean primaryNumber) {
		this.primaryNumber = primaryNumber;
	}

}
