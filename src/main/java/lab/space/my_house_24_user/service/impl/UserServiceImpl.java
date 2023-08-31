package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.mapper.UserMapper;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;
import lab.space.my_house_24_user.repository.UserRepository;
import lab.space.my_house_24_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(()->new EntityNotFoundException("User by email:"+username+" not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new EntityNotFoundException("User by email:"+email+" not found"));
    }

    @Override
    public Long getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("User by email:"+authentication.getName()+" not found")).getId();

    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User by id:"+id+" not found"));

    }

    @Override
    public void changeTheme(Boolean theme) {
        User user = findById(getCurrentUser());
        user.setTheme(theme);
        userRepository.save(user);
    }

    @Override
    public UserResponseForSidebar getUserForSidebar() {
        return UserMapper.entityToDtoForSidebar(findById(getCurrentUser()));
    }
}
