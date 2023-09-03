package auto.parts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import auto.parts.controller.model.AutoPartsCustomer;
import auto.parts.controller.model.AutoPartsData;
import auto.parts.controller.model.AutoPartsEmployee;
import auto.parts.service.AutoPartsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auto_parts")
@Slf4j
public class AutoPartsController {
	@Autowired
	private AutoPartsService autoPartsService;

	public AutoPartsController(AutoPartsService autoPartsService) {
		this.autoPartsService = autoPartsService;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public AutoPartsData createAutoParts(@RequestBody AutoPartsData autoPartsData) {
		log.info("Received a POST request to create auto parts: {}", autoPartsData);
		return autoPartsService.saveAutoParts(autoPartsData);
	}

	@PutMapping("/{autoPartsId}")
	public AutoPartsData updateAutoParts(@PathVariable Long autoPartsId, @RequestBody AutoPartsData autoPartsData) {

		// Set the auto parts ID in the updatedAutoPartsData
		autoPartsData.setAutoPartsId(autoPartsId);

		log.info("Received a PUT request to update auto parts with ID {}", autoPartsId);

		return autoPartsService.saveAutoParts(autoPartsData);
	}

	@PostMapping("/{autoPartsId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AutoPartsEmployee addEmployeeToAutoParts(@PathVariable Long autoPartsId,
			@RequestBody AutoPartsEmployee autoPartsEmployee) {

		log.info("Received a POST request to add an employee {} to auto parts with ID: {}", autoPartsEmployee,
				autoPartsId);

		return autoPartsService.saveEmployee(autoPartsId, autoPartsEmployee);
	}

	@PostMapping("/{autoPartsID}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AutoPartsCustomer addCustomerToAutoParts(@PathVariable Long autoPartsId,
			@RequestBody AutoPartsCustomer autoPartsCustomer) {
		log.info("Adding customer {} to auto parts store with ID= {}", autoPartsCustomer, autoPartsId);
		return autoPartsService.saveCustomer(autoPartsId, autoPartsCustomer);
	}

	@GetMapping
	public List<AutoPartsData> retreiveAllAutoParts() {
		log.info("Retrieving all auto parts stores");
		return autoPartsService.retrieveAllAutoParts();
	}

	@GetMapping("/{petStoreId}")
	public AutoPartsData retrieveAutoPartsById(@PathVariable Long autoPartsId) {
		log.info("Retrieving auto parts store with ID={}", autoPartsId);
		return autoPartsService.retrieveAutoPartsById(autoPartsId);
	}

	@DeleteMapping("/{autoPartsId}")
	public Map<String, String> deleteAutoPartsById(@PathVariable Long autoPartsId) {
		log.info("Deleting auto parts store with ID={}", autoPartsId);

		return Map.of("message", "Auto parts store with ID=" + autoPartsId + " deleted.");
	}

}
