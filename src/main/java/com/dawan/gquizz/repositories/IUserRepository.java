package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.User;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

}
