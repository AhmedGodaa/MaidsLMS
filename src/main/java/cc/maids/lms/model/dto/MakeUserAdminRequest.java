package cc.maids.lms.model.dto;

import java.util.List;

public class MakeUserAdminRequest {


    private String email;
    private List<String> authorities;

    public MakeUserAdminRequest(String email, List<String> authorities) {
        this.email = email;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
