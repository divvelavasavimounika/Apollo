package com.hdfc.irm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdfc.irm.entities.LoginRequestEntity;


@Repository
public interface LoginRepository extends JpaRepository<LoginRequestEntity, Long> {

}
