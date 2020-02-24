
package com.hdfc.irm.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdfc.irm.engine.entities.PennyTxnReferenceEntity;

@Repository
public interface PennyTxnReferenceRepository extends JpaRepository<PennyTxnReferenceEntity, Long> {

}
