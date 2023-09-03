package auto.parts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.parts.entity.AutoParts;

public interface AutoPartsDao extends JpaRepository<AutoParts, Long> {

}
