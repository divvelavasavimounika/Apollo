package com.hdfc.irm.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.hdfc.irm.engine.utils.IrmUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AUDIT_LOGIN")
public class LoginRequestEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "USER_ID")
	private String userid;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "SOURCE")
	private String source;
	@Column(name = "DEVICE_ID")
	private String device_id;
	@Column(name = "BUILD_VERSION_CODE")
	private String build_version_code;
	@Column(name = "CHANNEL_ID")
	private String channel_id;
	@Column(name = "OS")
	private String os;
	@Column(name = "TIME_STAMP")
	@CreationTimestamp
	private LocalDateTime time;

	@Override
	public String toString() {
		return "LoginRequestEntity [id=" + getId() + ", userid=" + getUserid() + ", password=" + IrmUtils.encodeString(getPassword())
				+ ", source=" + getSource() + ", device_id=" + getDevice_id() + ", build_version_code=" + getBuild_version_code()
				+ ", channel_id=" + getChannel_id() + ", os=" + getOs() + ", time=" + getTime() + "]";
	}

}
