package cc.maids.lms.model.dto;

import java.util.ArrayList;
import java.util.List;

public class UserSignInResponse {
    private String id;
    private String email;
    private String fullName;
    private String token;
    private String createdAt;
    private String updatedAt;
    private String address;
    private String city;
    private String phone;

    private boolean isEmailVerified = false;
    private boolean isPhoneVerified = false;
    private List<String> authorities = new ArrayList<>();

    public UserSignInResponse(String id, String email, String fullName, String token, String createdAt, String updatedAt, String address, String city, String phone, boolean isEmailVerified, boolean isPhoneVerified, List<String> authorities) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.isEmailVerified = isEmailVerified;
        this.isPhoneVerified = isPhoneVerified;
        this.authorities = authorities;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
