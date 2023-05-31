package br.com.tiago.restapiwithspringboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;

    @Column(name = "name_category", unique = false, nullable = false, length = 300)
    @Length(min = 2, max = 300, message="O nome deve ter ao menos dois  caracteres")
//    @NotBlank(massage = "O campo nome é obrigatório")
    private String nameCategory;

    @Column(name = "description_category", nullable = false, length = 1000)
    private String descriptionCategory;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(long idCategory) {
        this.idCategory = idCategory;
    }
}
