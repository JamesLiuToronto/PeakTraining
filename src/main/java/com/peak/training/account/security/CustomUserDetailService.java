package com.peak.training.account.security;

import com.peak.training.account.domain.model.Account;
import com.peak.training.account.domain.model.UserLogin;
import com.peak.training.account.domain.model.UserStatus;
import com.peak.training.account.infrastructure.adapter.AccountAdapter;
import com.peak.training.account.infrastructure.adapter.UserLoginAdapter;
import com.peak.training.common.exception.AppMessageException;
import org.peak.common.token.model.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    AccountAdapter accountAdapter ;

    @Autowired
    UserLoginAdapter loginAdapter ;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Account account = accountAdapter.getAccountByEmailAddress(userName);
        UserLogin login = loginAdapter.getUserLoginById(account.getUserId()) ;

        CurrentUser user= findByUserName(userName);
        System.out.println(user);

        //return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
        //        Arrays.stream(user.getRoles().split(","))
        //        .map(SimpleGrantedAuthority::new)
        //        .collect(Collectors.toList()));
        return findByUserName(userName) ;

    }

    private CurrentUser findByUserName(String userName) {
            Account account = accountAdapter.getAccountByEmailAddress(userName);
            UserLogin login = loginAdapter.getUserLoginById(account.getUserId());
            validate(account, login) ;
            return getCurrentUser(userName, account, login ) ;

    }
    private CurrentUser getCurrentUser(String userName, Account account, UserLogin login ){

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> authorities = Arrays.stream(("USER").split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) ;
        CurrentUser user =  new CurrentUser(userName,
                login.getPassword(),
                enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        user.setFirstName(account.getPersonInfo().getFirstName() + " " + account.getPersonInfo().getLastName());
        user.setLastName(Integer.toString(account.getUserId()));
        user.setRoles(account.getRoleList());
        return user ;
    }

    private void validate(Account account, UserLogin login){
        if ((account == null)||(account.getUserId() < 1))
            new UsernameNotFoundException("User Name is not found");
        if (account.getUserStatus() == UserStatus.DISABLE)
            throw new AppMessageException("login.validation.disable.user") ;
        if (login.isLocked())
            throw new AppMessageException("login.validation.locked") ;

    }

}
