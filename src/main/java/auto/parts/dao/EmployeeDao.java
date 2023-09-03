package auto.parts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.parts.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
