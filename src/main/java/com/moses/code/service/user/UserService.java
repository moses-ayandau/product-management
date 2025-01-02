package com.moses.code.service.user;

import com.moses.code.entity.User;
import com.moses.code.exception.EmailAlreadyExistException;
import com.moses.code.exception.UserNotFoundException;
import com.moses.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws EmailAlreadyExistException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists: " + user.getEmail());
        }
//        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        User user = getUserById(userId);
        userRepository.delete(user);
    }


}
