package com.moses.code.service.user;

import com.moses.code.utils.PasswordUtils;
import com.moses.code.entity.User;
import com.moses.code.exception.EmailAlreadyExistException;
import com.moses.code.exception.UnAuthenticatedException;
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
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
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
        userRepository.deleteById(userId);
    }

    @Override
    public User authenticate(String email, String password) throws UnAuthenticatedException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnAuthenticatedException("Invalid email or password"));

        if (!PasswordUtils.verifyPassword(password, user.getPassword())) {
            throw new UnAuthenticatedException("Invalid email or password");
        }

        return user;
    }
}
