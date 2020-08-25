package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface ConfirmationTokenDao extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
