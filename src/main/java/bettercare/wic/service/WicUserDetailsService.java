package bettercare.wic.service;

import bettercare.wic.dal.entity.User;
import bettercare.wic.model.WicUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class WicUserDetailsService implements UserDetailsService {

    @Autowired EntityService entityService;

    @Override
    public WicUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new WicUserDetails(findUser(username));
    }

    private User findUser(String username) {
        User user = entityService.findUserByName(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
