package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.dto.ProductDTO;
import com.PTIV.loja.de.games.exceptions.CategoryNotFoundException;
import com.PTIV.loja.de.games.model.Category;
import com.PTIV.loja.de.games.model.Product;
import com.PTIV.loja.de.games.service.CategoryService;
import com.PTIV.loja.de.games.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // setting a variable for the directory of the product images
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";

    //admin home page
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }


    //categories page for CMS
    @GetMapping("/admin/categories")
    public String viewCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/categories";
    }

    //Add Category page for CMS
    @GetMapping("/admin/categories/add")
    public String getAddCategory(Model model){
        //thymeleaf is looking for a "category" obj in the front-end
        // we define a new Category obj with alias "category"
        Category category = new Category();
        model.addAttribute("category", category);
        return "categoriesAdd";
    }

    //Add Category
    @PostMapping("/admin/categories/add")
    public String postAddCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    //Delete Category
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }

    //Products page CMS
    @GetMapping("/admin/products")
    public String viewProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    //Add Products Page
    @GetMapping("/admin/products/add")
    public String getAddProducts(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "productsAdd";
    }

    //Add Product
    @PostMapping("/admin/products/add")
    public String postAddProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile multipartFile,
                                  @RequestParam("imgName") String imageName) throws IOException {

        // Use o productDTO para transferir os atributos para o objeto de produto
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());

        // Precisamos passar a categoria para o produto
        // O ProductDTO só tem o ID da categoria
        Integer categoryId = productDTO.getCategoryId();

        // Use o método getOrElseThrow para obter a categoria
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));

        product.setCategory(category);
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());

        // Nome da imagem
        String imageIdentifier;

        // Se o objeto multipartFile não estiver vazio, significa que uma nova imagem foi enviada
        if (!multipartFile.isEmpty()) {
            imageIdentifier = multipartFile.getOriginalFilename();

            // Caminho onde a imagem será salva
            Path fileNameAndPath = Paths.get(uploadDir, imageIdentifier);

            // Salve o arquivo no caminho
            Files.write(fileNameAndPath, multipartFile.getBytes());
        } else {
            // Se o objeto multipartFile estiver vazio, use o nome da imagem fornecido
            imageIdentifier = imageName;
        }

        // Defina os últimos atributos do produto
        product.setImageName(imageIdentifier);

        // Salve o objeto no banco de dados
        productService.addProduct(product);

        // Redirecione para a página de produtos
        return "redirect:/admin/products";
    }

    //Delete Product
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }
}


