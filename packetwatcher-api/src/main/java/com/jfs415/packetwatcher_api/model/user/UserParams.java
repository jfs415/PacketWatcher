package com.jfs415.packetwatcher_api.model.user;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
@Setter
public class UserParams {

    /**
     * POJO to hold @RequestParam values to create a User object.
     * Purpose of this class is to reduce number of parameters passed
     * to NewUserController::process.
     */
    @NotBlank(message = "First name cannot be empty!")
    private String first;

    @NotBlank(message = "Last name cannot be empty!")
    private String last;

    @NotBlank(message = "Email name cannot be empty!")
    private String email;

    @Nullable private String phone;

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    private String password;

    @Override
    public String toString() {
        return first + " " + last + " " + email + " " + phone + " " + username + " " + password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserParams userParams) {
            return this.username.equals(userParams.username)
                    && this.password.equals(userParams.password)
                    && this.first.equals(userParams.first)
                    && this.last.equals(userParams.last)
                    && this.email.equals(userParams.email);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31
                * (username.hashCode()
                        + password.hashCode()
                        + email.hashCode()
                        + first.hashCode()
                        + this.last.hashCode());
    }
}
