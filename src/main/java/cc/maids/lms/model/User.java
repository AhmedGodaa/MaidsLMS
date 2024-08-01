package cc.maids.lms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;

    private String email;
    private String fullName;

    private List<String> authorities = new ArrayList<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private String createdAt;
    private String updatedAt;
    private String address;
    private String city;
    private String phone;

    private boolean isEmailVerified = false;
    private boolean isPhoneVerified = false;


    public User(String id, String email, String fullName, List<String> authorities, String password, String createdAt, String updatedAt, String address, String city, String phone, boolean isEmailVerified, boolean isPhoneVerified) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.authorities = authorities;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.isEmailVerified = isEmailVerified;
        this.isPhoneVerified = isPhoneVerified;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
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
}