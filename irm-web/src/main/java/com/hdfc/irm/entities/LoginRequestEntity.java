package com.hdfc.irm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Login")
public class LoginRequestEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String userid;
	@Column(name="Password")
	private String password;
	@Column(name="Source")
	private String source;
	@Column(name="Device_ID")
	private String device_id;
	@Column(name="Build_Version_Code")
	private String build_version_code;
	@Column(name="Channel_ID")
	private String channel_id;
	@Column(name="OS")
	private String os;


}
