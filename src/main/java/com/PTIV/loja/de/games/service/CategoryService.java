package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.Category;
import com.PTIV.loja.de.games.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }

    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
    }
}
