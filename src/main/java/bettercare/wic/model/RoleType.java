package bettercare.wic.model;

import bettercare.wic.dal.entity.Role;

public enum RoleType {

        USER,
        ADMIN;

        public Role getRole() {
            Role role = new Role();
            role.setName(this.name());
//            return new Role(this.name());
            return role;
        }
}
