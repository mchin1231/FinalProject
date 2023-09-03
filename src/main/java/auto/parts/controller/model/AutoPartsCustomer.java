package auto.parts.controller.model;

import auto.parts.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutoPartsCustomer {

	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;

	public AutoPartsCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
	}
}
