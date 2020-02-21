
package com.hdfc.irm.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hdfc.irm.engine.entities.PennyDropRequestEntity;

@Repository
public interface AuditPennyDropRepository extends JpaRepository<PennyDropRequestEntity, Long> {

	@Query(value = "SELECT PNY_TXN_REFERENCE_ID.NEXTVAL from DUAL", nativeQuery = true)
	Long fetchNexReferenceValue();
}
