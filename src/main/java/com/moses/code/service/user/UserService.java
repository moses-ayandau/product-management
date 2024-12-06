package com.moses.code.service.user;

import com.moses.code.entity.Product;
import com.moses.code.entity.User;
import com.moses.code.exception.EmailAlreadyExistException;
import com.moses.code.exception.UserNotFoundException;
import com.moses.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws EmailAlreadyExistException {

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void disableUser(Long userId) throws UserNotFoundException {

    }

    @Override
    public void deleteUserPermanently(Long userId) throws UserNotFoundException {
    userRepository.deleteById(userId);
    }

    @Override
    public List<Product> getProductsByUserId(Long userId) throws UserNotFoundException {

        return List.of();
    }
}
