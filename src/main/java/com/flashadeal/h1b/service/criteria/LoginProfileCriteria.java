package com.flashadeal.h1b.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.flashadeal.h1b.domain.LoginProfile} entity. This class is used
 * in {@link com.flashadeal.h1b.web.rest.LoginProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /login-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LoginProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private StringFilter userId;

    private StringFilter memberId;

    private StringFilter phoneNumber;

    private StringFilter emailId;

    private StringFilter password;

    private StringFilter status;

    private StringFilter activationCode;

    private Boolean distinct;

    public LoginProfileCriteria() {}

    public LoginProfileCriteria(LoginProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.emailId = other.emailId == null ? null : other.emailId.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.activationCode = other.activationCode == null ? null : other.activationCode.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoginProfileCriteria copy() {
        return new LoginProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public StringFilter userName() {
        if (userName == null) {
            userName = new StringFilter();
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public StringFilter userId() {
        if (userId == null) {
            userId = new StringFilter();
        }
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getMemberId() {
        return memberId;
    }

    public StringFilter memberId() {
        if (memberId == null) {
            memberId = new StringFilter();
        }
        return memberId;
    }

    public void setMemberId(StringFilter memberId) {
        this.memberId = memberId;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getEmailId() {
        return emailId;
    }

    public StringFilter emailId() {
        if (emailId == null) {
            emailId = new StringFilter();
        }
        return emailId;
    }

    public void setEmailId(StringFilter emailId) {
        this.emailId = emailId;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getActivationCode() {
        return activationCode;
    }

    public StringFilter activationCode() {
        if (activationCode == null) {
            activationCode = new StringFilter();
        }
        return activationCode;
    }

    public void setActivationCode(StringFilter activationCode) {
        this.activationCode = activationCode;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LoginProfileCriteria that = (LoginProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(emailId, that.emailId) &&
            Objects.equals(password, that.password) &&
            Objects.equals(status, that.status) &&
            Objects.equals(activationCode, that.activationCode) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userId, memberId, phoneNumber, emailId, password, status, activationCode, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginProfileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userName != null ? "userName=" + userName + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (emailId != null ? "emailId=" + emailId + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (activationCode != null ? "activationCode=" + activationCode + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
