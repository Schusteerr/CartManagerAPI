package com.cartmanager.repository;

import jakarta.transaction.Transactional;
import com.cartmanager.entity.Product;
import com.cartmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.cart = :newItem WHERE u.email = :email")
    int updateCart(String email, Product newItem);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :newStatus WHERE u.email = :email")
    int updateStatus(String email, String newStatus);

}


