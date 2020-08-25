package io.github.zahrarsl.exam.model.dao;


import io.github.zahrarsl.exam.model.entity.User;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface UserDao extends Repository<User, Integer> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    User findById(int id);
}
