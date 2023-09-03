package auto.parts.controller.model;

import auto.parts.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutoPartsEmployee {

	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;

	public AutoPartsEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhone = employee.getEmployeePhone();
		employeeJobTitle = employee.getEmployeeJobTitle();
	}
}
