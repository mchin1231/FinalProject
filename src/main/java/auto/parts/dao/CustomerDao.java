package auto.parts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.parts.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
