package br.com.tiago.restapiwithspringboot.service;

import br.com.tiago.restapiwithspringboot.entity.Category;
import br.com.tiago.restapiwithspringboot.entity.Product;
import br.com.tiago.restapiwithspringboot.repository.CategoryRepository;
import br.com.tiago.restapiwithspringboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getInfoCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        if (validateCategory(category)) {         // se a validação for verdadeira, cadastre!
            return categoryRepository.saveAndFlush(category); // saveandflush confirma o que ele fez, é mais rápido que o método só save
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O preço de custo e preço de venda do produto são " +
                            "obrigatórios e devem ser maiores que 0 (zero)!");
        }
    }

    public HashMap<String, Object> deleteCategory(Long categoryId) {
        Optional<Category> category = // criei uma variavel chamada category. O optional é uma classe do java que procura os objetos
                Optional.ofNullable(categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, //Optional.ofNullable ---> encontre o produto pelo id, se não for nulo ele pega o id, se for nulo, ele da a mensagem
                        "Produto não encontrado!")));                           // o throw é o tratamento de exceções

        categoryRepository.delete(category.get());
        HashMap<String, Object> result = new  HashMap<String, Object> (); // HashMap --- > monto uma variável da forma que eu quiser
        result.put("result", "Produto: " + category.get().getNameCategory() + " excluído com sucesso!");
        return result;
    }

    public Category findCategoryById(Long idCategory){
        return categoryRepository.findById(idCategory)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
    }

    public Category updateCategory(Category category) {

        if (category.getIdCategory() == null || category.getIdCategory() <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O ID do produto é obrigatório na atualização!");
        }

        if (validateCategory(category)) {
            if(findCategoryById(category.getIdCategory()) != null) {
                return categoryRepository.saveAndFlush(category);
            } else{
                return null;
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço de custo e preço de venda do produto são obrigatórios e devem ser maiores que 0 (zero)!");
        }

    }

    public Boolean validateCategory (Category category) {
        if (category.getNameCategory() != null && category.getDescriptionCategory() != null){
            return true;
        } else {
            return false;
        }
    }

}