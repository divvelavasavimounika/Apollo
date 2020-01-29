
package com.hdfc.irm.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdfc.irm.engine.entities.DecisionRequestEntity;

@Repository
public interface AuditDecisionRepository extends JpaRepository<DecisionRequestEntity, String> {

}
