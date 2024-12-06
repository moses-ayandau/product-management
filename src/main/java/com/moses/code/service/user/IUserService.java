package com.moses.code.service.user;

import com.moses.code.entity.Product;
import com.moses.code.entity.User;
import com.moses.code.exception.EmailAlreadyExistException;
import com.moses.code.exception.UserNotFoundException;

import java.util.List;

public interface IUserService {
    User createUser(User user) throws EmailAlreadyExistException;
    User getUserById(Long userId) throws UserNotFoundException;
    List<User> getUsers();
    void disableUser(Long userId) throws UserNotFoundException;
    void deleteUserPermanently(Long userId) throws UserNotFoundException;
    List<Product> getProductsByUserId(Long userId) throws UserNotFoundException;

}
