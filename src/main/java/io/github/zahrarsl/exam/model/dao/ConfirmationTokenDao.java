package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ConfirmationTokenDao extends Repository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken save(ConfirmationToken confirmationToken);
}
