package com.example.demo.repositories;

import com.example.demo.model.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserData, Long> {
}
