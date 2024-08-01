package cc.maids.lms.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserSignInRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    public UserSignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
