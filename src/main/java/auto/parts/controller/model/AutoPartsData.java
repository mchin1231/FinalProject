package auto.parts.controller.model;

import java.util.HashSet;
import java.util.Set;

import auto.parts.entity.AutoParts;
import auto.parts.entity.Customer;
import auto.parts.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutoPartsData {
	private Long autoPartsId;
	private String autoPartsName;
	private String autoPartsAddress;
	private String autoPartsCity;
	private String autoPartsState;
	private String autoPartsZip;
	private String autoPartsPhone;
	private Set<AutoPartsCustomer> customers = new HashSet<>();
	private Set<AutoPartsEmployee> employees = new HashSet<>();

	public AutoPartsData(AutoParts autoParts) {
		autoPartsId = autoParts.getAutoPartsId();
		autoPartsName = autoParts.getAutoPartsName();
		autoPartsAddress = autoParts.getAutoPartsAddress();
		autoPartsCity = autoParts.getAutoPartsCity();
		autoPartsState = autoParts.getAutoPartsState();
		autoPartsZip = autoParts.getAutoPartsZip();
		autoPartsPhone = autoParts.getAutoPartsPhone();

		for (Customer customer : autoParts.getCustomers()) {
			customers.add(new AutoPartsCustomer(customer));
		}

		for (Employee employee : autoParts.getEmployees()) {
			employees.add(new AutoPartsEmployee(employee));
		}
	}

	public AutoPartsData(Long autoPartsId, String partName, Set<AutoPartsCustomer> customers,
			Set<AutoPartsEmployee> employees) {
		this.autoPartsId = autoPartsId;
		this.autoPartsName = partName;
		this.customers = customers;
		this.employees = employees;
	}

}
