package com.inventory.pe.dao;

import com.inventory.pe.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("select p from Product p  where p.name like %?1%")
   List<Product> findByNameLike(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

}
