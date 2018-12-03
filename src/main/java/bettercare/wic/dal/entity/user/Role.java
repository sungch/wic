package bettercare.wic.dal.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NaturalId
    private String name;

    @JsonManagedReference("role_ref")
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUsers(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return String.format("id:%s name:%s ", this.getId(), this.getName());
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
        if (!(thatObj instanceof Role)) {
            return false;
        }
        Role that = (Role) thatObj;
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