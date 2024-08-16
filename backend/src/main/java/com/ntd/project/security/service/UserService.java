package com.ntd.project.security.service;

import com.ntd.project.security.model.UserModel;
import com.ntd.project.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public UserModel getUserDetais(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean userCanOperate(UserModel user, Long amount) {
        var balance = user.getBalance();
        return balance >= amount;
    }

    public UserModel decreaseUserBalance(UserModel user, Long amount) {
        if (userCanOperate(user, amount)) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            return user;
        }
        throw new IllegalStateException("User does not have enough balance to carry out the operation");
    }


}
