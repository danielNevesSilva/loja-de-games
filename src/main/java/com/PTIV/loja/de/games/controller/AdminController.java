package com.PTIV.loja.de.games.controller;

import com.PTIV.loja.de.games.dto.ProductDTO;
import com.PTIV.loja.de.games.exceptions.CategoryNotFoundException;
import com.PTIV.loja.de.games.model.Category;
import com.PTIV.loja.de.games.model.Product;
import com.PTIV.loja.de.games.service.CategoryService;
import com.PTIV.loja.de.games.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // definindo uma variável para o diretório das imagens do produto
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/Images";


    //página inicial do administrador
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }


    //página de categorias para CMS
    @GetMapping("/admin/categories")
    public String viewCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/categories";
    }

    //Adicionar página de categoria para CMS
    @GetMapping("/admin/categories/add")
    public String getAddCategory(Model model){
        //thymeleaf está procurando um objeto de "categoria" no front-end
        // definimos um novo objeto de categoria com o alias "category"
        Category category = new Category();
        model.addAttribute("category", category);
        return "categoriesAdd";
    }

    //Adicionar categoria
    @PostMapping("/admin/categories/add")
    public String postAddCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    //Excluir categoria
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }

    //CMS da página de produtos
    @GetMapping("/admin/products")
    public String viewProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    //Página Adicionar Produtos
    @GetMapping("/admin/products/add")
    public String getAddProducts(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "productsAdd";
    }

    //Adicionar Produtos
    @PostMapping("/admin/products/add")
    public String postAddProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile multipartFile,
                                  @RequestParam("imgName") String imageName) throws IOException {

//       Use o objeto productDTO para transferir os atributos para o objeto produto
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());

//        precisamos passar a categoria para o produto
//        o ProductDTO possui apenas o id da categoria
        Integer categoryId = productDTO.getCategoryId();
        String categoryName = productDTO.getName();

        // definir os atributos do produto
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setRating(productDTO.getRating());

        //Nome da imagem
        String imageIdentifier;
        //se o objeto multipartFile não estiver vazio ao passar do front-end
        if(!multipartFile.isEmpty()){
            imageIdentifier = multipartFile.getOriginalFilename();
            // isso cria o caminho onde a imagem deve ser carregada no sistema
            Path fileNameAndPath = Paths.get(uploadDir, imageIdentifier);
            //this writes the actual file and uploads to the system
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }else {
            //o arquivo está vazio
            imageIdentifier = imageName;
        }
        //definir os últimos atributos do produto
        product.setImageName(imageIdentifier);
        //salve o objeto em db
        productService.addProduct(product);

        //redirecionar para a página de produtos
        return "redirect:/admin/products";
    }

    //Deleta produto
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

}

