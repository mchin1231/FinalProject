package auto.parts.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class AutoParts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long autoPartsId;
	private String autoPartsName;
	private String autoPartsAddress;
	private String autoPartsCity;
	private String autoPartsState;
	private String autoPartsZip;
	private String autoPartsPhone;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "auto_parts_customer", joinColumns = @JoinColumn(name = "auto_parts_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Customer> customers = new HashSet<>();

	@OneToMany(mappedBy = "autoParts", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Employee> employees = new HashSet<>();

}
