package bettercare.wic.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserRoleModel {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotEmpty
    private List<String> rolename;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRolename() {
        return rolename;
    }

    public void setRolename(List<String> rolename) {
        this.rolename = rolename;
    }
}
