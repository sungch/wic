package bettercare.wic.dal.entity.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * The persistent class for the customer database table.
 *
 * @Embeddable type must be Serializable
 *
 * The @Embeddable type must override the default equals and hashCode methods
 * based on the two Primary Key identifier values
 *
 */
@Embeddable
public class UserRoleId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "role_id")
    private long roleId;

    public UserRoleId(long uid, long rid) {
        this.userId = uid;
        this.roleId = rid;
    }

    private UserRoleId() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(this.userId, that.userId) && Objects.equals(this.roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }

    @Override
    public String toString() {
        return Long.toString(this.getRoleId()) + Long.toString(this.getRoleId());
    }
}