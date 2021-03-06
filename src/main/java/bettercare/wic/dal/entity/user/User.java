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

    @JsonManagedReference("user_ref")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
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

    @Override
    public String toString() {
        return String.format("id:%s roles:%s ", this.getId(), Arrays.deepToString(this.getUserRoles().toArray()));
    }

    /**
     * Compute int for fields that defines equals()
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
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