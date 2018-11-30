package bettercare.wic.dal.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

/**
 * The persistent class for the customer database table.
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NaturalId
    private String username;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRole> userRoles = new ArrayList<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        this.userRoles.add(userRole);
        role.getUserRoles().add(userRole);
    }

    public void removeRole(Role role) {
        for(Iterator<UserRole> I = getUserRoles().iterator(); I.hasNext();) {
            UserRole userRole = I.next();
            if(userRole.getUser().equals(this) && userRole.getRole().equals(role)) {
                I.remove();
                userRole.getRole().getUserRoles().remove(userRole);
                userRole.setUser(null);
                userRole.setRole(null);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("id:%s roles:%s ", this.getId(), Arrays.deepToString(this.getUserRoles().toArray()));
    }

    /**
     * Compute int for fields that defines equals()
     */
    @Override
    public int hashCode() {
        return Long.valueOf(this.getId()).hashCode() + Long.valueOf(getStringHash(Arrays.deepToString(this.getUserRoles().toArray()))).hashCode();
    }

    private int getStringHash(String val) {
        return val == null ? 0 : val.hashCode();
    }

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null) {
            return false;
        }
        if (!(thatObj instanceof User)) {
            return false;
        }
        User that = (User) thatObj;
        return isSame(that.toString(), this.toString());
    }

    private boolean isSame(String that, String me) {
        if (that == null) {
            return me == null;
        }
        if (me == null) {
            return false;
        }
        return that.equals(me);
    }

}