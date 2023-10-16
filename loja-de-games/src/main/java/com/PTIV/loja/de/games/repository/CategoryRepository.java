package com.PTIV.loja.de.games.repository;


import com.PTIV.loja.de.games.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
