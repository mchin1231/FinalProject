package auto.parts.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.parts.controller.model.AutoPartsCustomer;
import auto.parts.controller.model.AutoPartsData;
import auto.parts.controller.model.AutoPartsEmployee;
import auto.parts.dao.AutoPartsDao;
import auto.parts.dao.CustomerDao;
import auto.parts.dao.EmployeeDao;
import auto.parts.entity.AutoParts;
import auto.parts.entity.Customer;
import auto.parts.entity.Employee;

@Service
public class AutoPartsService {

	@Autowired
	private final AutoPartsDao autoPartsDao;
	private final EmployeeDao employeeDao;
	private final CustomerDao customerDao;

	@Autowired
	public AutoPartsService(AutoPartsDao autoPartsDao, EmployeeDao employeeDao, CustomerDao customerDao) {
		this.autoPartsDao = autoPartsDao;
		this.employeeDao = employeeDao;
		this.customerDao = customerDao;
	}

	@Transactional(readOnly = false)
	public AutoPartsData saveAutoParts(AutoPartsData autoPartsData) {
		Long autoPartsId = autoPartsData.getAutoPartsId();
		AutoParts autoParts = findOrCreateAutoParts(autoPartsId);
		copyAutoPartsFields(autoParts, autoPartsData);

		AutoParts savedAutoParts = autoPartsDao.save(autoParts);
		return new AutoPartsData(savedAutoParts);
	}

	private AutoParts findOrCreateAutoParts(Long autoPartsId) {
		if (Objects.isNull(autoPartsId)) {
			return new AutoParts();
		} else {
			return findAutoPartsById(autoPartsId);
		}
	}

	private AutoParts findAutoPartsById(Long autoPartsId) {
		return autoPartsDao.findById(autoPartsId)
				.orElseThrow(() -> new NoSuchElementException("AutoParts with ID " + autoPartsId + " not found"));
	}

	private void copyAutoPartsFields(AutoParts autoParts, AutoPartsData autoPartsData) {
		autoParts.setAutoPartsAddress(autoPartsData.getAutoPartsAddress());
		autoParts.setAutoPartsCity(autoPartsData.getAutoPartsCity());
		autoParts.setAutoPartsId(autoPartsData.getAutoPartsId());
		autoParts.setAutoPartsName(autoPartsData.getAutoPartsName());
		autoParts.setAutoPartsPhone(autoPartsData.getAutoPartsPhone());
		autoParts.setAutoPartsState(autoPartsData.getAutoPartsState());
		autoParts.setAutoPartsZip(autoPartsData.getAutoPartsZip());
	}

	private void copyEmployeeFields(Employee employee, AutoPartsEmployee autoPartsEmployee) {
		// Copy matching fields from AutoPartsEmployee to Employee
		employee.setEmployeeFirstName(autoPartsEmployee.getEmployeeFirstName());
		employee.setEmployeeId(autoPartsEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(autoPartsEmployee.getEmployeeJobTitle());
		employee.setEmployeeLastName(autoPartsEmployee.getEmployeeLastName());
		employee.setEmployeePhone(autoPartsEmployee.getEmployeePhone());

		// Add more fields as needed
	}

	@SuppressWarnings("unused")
	private void copyCustomerFields(Customer customer, AutoPartsCustomer autoPartsCustomer) {
		customer.setCustomerEmail(autoPartsCustomer.getCustomerEmail());
		customer.setCustomerFirstName(autoPartsCustomer.getCustomerFirstName());
		customer.setCustomerId(autoPartsCustomer.getCustomerId());
		customer.setCustomerLastName(autoPartsCustomer.getCustomerLastName());

	}

	private Employee findOrCreateEmployee(Long autoPartsId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		}
		return findEmployeeById(autoPartsId, employeeId);
	}

	private Customer findOrCreateCustomer(Long autoPartsId, Long customerId) {
		if (Objects.isNull(customerId)) {
			return new Customer();
		}
		return findCustomerById(autoPartsId, customerId);
	}

	private Employee findEmployeeById(Long autoPartsId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));

		if (employee.getAutoParts().getAutoPartsId() != autoPartsId) {
			throw new IllegalArgumentException("The employee with ID=" + employeeId
					+ " is not employed by the auto parts store with ID=" + autoPartsId + ".");
		}

		return employee;
	}

	private Customer findCustomerById(Long autoPartsId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));

		boolean found = false;

		for (AutoParts autoParts : customer.getAutoParts()) {
			if (autoParts.getAutoPartsId() == autoPartsId) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new IllegalArgumentException("The customer with ID=" + customerId
					+ " is not a member of the auto parts store with the ID=" + autoPartsId);
		}
		return customer;
	}

	@Transactional(readOnly = false)
	public AutoPartsEmployee saveEmployee(Long autoPartsId, AutoPartsEmployee autoPartsEmployee) {
		AutoParts autoParts = findAutoPartsById(autoPartsId);
		Long employeeId = autoPartsEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(autoPartsId, employeeId);

		copyEmployeeFields(employee, autoPartsEmployee);

		employee.setAutoParts(autoParts);
		autoParts.getEmployees().add(employee);

		Employee dbEmployee = employeeDao.save(employee);

		return new AutoPartsEmployee(dbEmployee);
	}

	@Transactional
	public AutoPartsCustomer saveCustomer(Long autoPartsId, AutoPartsCustomer autoPartCustomer) {
		AutoParts autoParts = findAutoPartsById(autoPartsId);
		Long customerId = autoPartsCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(autoPartsId, customerId);

		copyCustomerFields(customer, autoPartsCustomer);

		customer.getAutoParts().add(autoParts);
		autoParts.getCustomers().add(customer);

		Customer dbCustomer = customerDao.save(customer);

		return new AutoPartsCustomer(dbCustomer);
	}

	@Transactional(readOnly = true)
	public List<AutoPartsData> retrieveAllAutoParts() {
		List<AutoParts> autoParts = autoPartsDao.findAll();

		List<AutoPartsData> result = new LinkedList<>();

		for (AutoParts autoPart : autoParts) {
			AutoPartsData apd = new AutoPartsData(autoParts);

			apd.getCustomers().clear();
			apd.getEmployees().clear();

			result.add(apd);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public AutoPartsData retrieveAutoPartsById(Long autoPartsId) {
		return new AutoPartsData(findAutoPartsById(autoPartsId));
	}

	@Transactional(readOnly = false)
	public void deleteAutoPartsById(Long autoPartsId) {
		AutoParts autoParts = findAutoPartsById(autoPartsId);
		autoPartsDao.delete(autoParts);
	}
}
