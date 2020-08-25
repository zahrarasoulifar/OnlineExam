package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Admin;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface AdminDao extends Repository<Admin, Integer> {
    Admin save(Admin admin);
}
