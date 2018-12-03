package bettercare.wic.service;

import bettercare.wic.dal.entity.user.User;
import bettercare.wic.model.WicUserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class WicUserDetailsService implements UserDetailsService {

    @Autowired EntityService entityService;

    @Override
    public WicUserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return new WicUserDetailsModel(findUser(username));
    }

    private User findUser(String username) {
        User user = entityService.findUserByName(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
