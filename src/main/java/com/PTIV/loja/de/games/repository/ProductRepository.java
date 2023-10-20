package com.PTIV.loja.de.games.repository;

import com.PTIV.loja.de.games.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Integer> {
    List<Product> findAllByCategory_Id(Integer id);

}
