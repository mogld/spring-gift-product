package gift;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Product product) {
        String productSql = "INSERT INTO products (name, price, imageUrl) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            return ps;
        }, keyHolder);

        Long productId = keyHolder.getKey().longValue();
        product.setId(productId);

        String optionSql = "INSERT INTO options (product_id, name, price) VALUES (?, ?, ?)";
        for (Option option : product.getOptions()) {
            jdbcTemplate.update(optionSql, productId, option.getName(), option.getPrice());
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, productRowMapper());
    }

    @Override
    public void update(Product product) {
        String productSql = "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(productSql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());

        String deleteOptionsSql = "DELETE FROM options WHERE product_id = ?";
        jdbcTemplate.update(deleteOptionsSql, product.getId());

        String optionSql = "INSERT INTO options (product_id, name, price) VALUES (?, ?, ?)";
        for (Option option : product.getOptions()) {
            jdbcTemplate.update(optionSql, product.getId(), option.getName(), option.getPrice());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setImageUrl(rs.getString("imageUrl"));

            String optionSql = "SELECT * FROM options WHERE product_id = ?";
            List<Option> options = jdbcTemplate.query(optionSql, new Object[]{product.getId()}, optionRowMapper());
            product.setOptions(options);

            return product;
        };
    }

    private RowMapper<Option> optionRowMapper() {
        return (rs, rowNum) -> {
            Option option = new Option();
            option.setId(rs.getLong("id"));
            option.setName(rs.getString("name"));
            option.setPrice(rs.getInt("price"));
            return option;
        };
    }
}
