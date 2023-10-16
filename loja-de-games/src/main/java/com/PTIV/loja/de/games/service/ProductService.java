package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.Product;
import com.PTIV.loja.de.games.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
        @Autowired
        private ProductRepository productRepository;

        public List<Product> getAllProducts(){
            return productRepository.findAll();
        }

        public void addProduct(Product product){
            productRepository.save(product);
        }

        public void deleteProductById(Integer id){
            productRepository.deleteById(id);
        }

        public Optional<Product> getProductById(Integer id){
            return productRepository.findById(id);
        }

        public List<Product> getAllProductsByCategoryId(Integer id){
            return productRepository.findAllByCategory_Id(id);
        }
}
