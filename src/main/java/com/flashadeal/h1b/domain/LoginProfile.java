package com.flashadeal.h1b.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoginProfile.
 */
@Entity
@Table(name = "login_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoginProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "activation_code")
    private String activationCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoginProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public LoginProfile userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return this.userId;
    }

    public LoginProfile userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public LoginProfile memberId(String memberId) {
        this.setMemberId(memberId);
        return this;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public LoginProfile phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public LoginProfile emailId(String emailId) {
        this.setEmailId(emailId);
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return this.password;
    }

    public LoginProfile password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return this.status;
    }

    public LoginProfile status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivationCode() {
        return this.activationCode;
    }

    public LoginProfile activationCode(String activationCode) {
        this.setActivationCode(activationCode);
        return this;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoginProfile)) {
            return false;
        }
        return id != null && id.equals(((LoginProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginProfile{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", userId='" + getUserId() + "'" +
            ", memberId='" + getMemberId() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", password='" + getPassword() + "'" +
            ", status='" + getStatus() + "'" +
            ", activationCode='" + getActivationCode() + "'" +
            "}";
    }
}
