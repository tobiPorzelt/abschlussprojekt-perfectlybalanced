package de.hhu.abschlussprojektverleihplattform.repository;

import de.hhu.abschlussprojektverleihplattform.database.ProductEntityRowMapper;
import de.hhu.abschlussprojektverleihplattform.model.ProductEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository
public class ProductRepository implements IProductRepository {


    final JdbcTemplate jdbcTemplate;

    final
    UserRepository userRepository;


    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }


    @Override
    public ProductEntity getProductById(Long id) {
        return (ProductEntity) jdbcTemplate.queryForObject("SELECT * FROM PRODUCT_ENTITY WHERE id=?",
                new Object[]{id},
                new ProductEntityRowMapper(userRepository));
    }

    @Override
    public ProductEntity getProductByTitlel(String title) {
        return (ProductEntity) jdbcTemplate.queryForObject("SELECT * FROM PRODUCT_ENTITY WHERE TITLE=?",
                new Object[]{title},
                new ProductEntityRowMapper(userRepository));
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return (List<ProductEntity>)jdbcTemplate.query("SELECT * FROM PRODUCT_ENTITY",
                new Object[]{},
                new ProductEntityRowMapper(userRepository));

    }

    @Override
    public void saveProduct(ProductEntity product) {
        jdbcTemplate.update(
                "INSERT INTO PRODUCT_ENTITY (COST, DESCRIPTION, CITY, HOUSENUMBER, POSTCODE, STREET, SURETY, TITLE, OWNER_USER_ID)" +
                        "VALUES (?,?,?,?,?,?,?,?,?)",
                product.getCost(),
                product.getDescription(),
                product.getLocation().getCity(),
                product.getLocation().getHousenumber(),
                product.getLocation().getPostcode(),
                product.getLocation().getStreet(),
                product.getSurety(),
                product.getTitle(),
                product.getOwner().getUserId());
    }

}