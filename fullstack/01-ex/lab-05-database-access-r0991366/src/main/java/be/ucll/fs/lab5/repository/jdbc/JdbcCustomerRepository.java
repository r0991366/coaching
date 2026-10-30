package be.ucll.fs.lab5.repository.jdbc;

import be.ucll.fs.lab5.model.Customer;
import be.ucll.fs.lab5.model.DrivingLicense;
import be.ucll.fs.lab5.repository.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

// ----- ignore hte errors on table names it means nothing (kinda) ----

@Primary
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcClient jdbcClient;

    public JdbcCustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Customer> findAll() {
        //RowMappers
        RowMapper<Customer> customerMapper = (rs, rowNum) -> {
            Customer c = new Customer(rs.getString("c_name"), rs.getString("c_first_name"), rs.getBoolean("c_passed"));
            c.setId(rs.getLong("c_id"));
            return c;
        };

        RowMapper<DrivingLicense> licenseMapper = (rs, rowNum) -> {
            Long id = rs.getLong("dl_id");
            if (rs.wasNull()) return null;  // no license
            DrivingLicense dl = new DrivingLicense(rs.getString("dl_license"), rs.getString("dl_transmission"));
            dl.setId(id);
            return dl;
        };

        //ResultSetExtractor
        ResultSetExtractor<List<Customer>> extractor = rs -> {
            Map<Long, Customer> map = new LinkedHashMap<>();

            while (rs.next()) {
                Long id = rs.getLong("c_id");

                // Create or reuse existing customer
                Customer c = map.computeIfAbsent(id, k -> {
                    try {
                        return customerMapper.mapRow(rs, rs.getRow());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Add license if present
                DrivingLicense dl = licenseMapper.mapRow(rs, rs.getRow());
                if (dl != null) {
                    c.getDrivingLicenses().add(dl);
                }
            }

            return new ArrayList<>(map.values());
        };

        return jdbcClient.sql("""
            SELECT  
            c.id              AS c_id,
            c.name            AS c_name,
            c.first_name      AS c_first_name,
            c.passed_theory_exam AS c_passed,
            dl.id             AS dl_id,
            dl.license        AS dl_license,
            dl.transmission   AS dl_transmission
            FROM customer c
            LEFT JOIN customer_driving_license cdl ON c.id = cdl.customer_id
            LEFT JOIN driving_license dl ON cdl.driving_license_id = dl.id
            ORDER BY c.id
            """)
                .query(extractor);
        }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            // CREATE
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcClient.sql("""
                INSERT INTO customer(name, first_name, passed_theory_exam)
                VALUES (:name, :firstName, :passedTheoryExam)
                """)
                    .param("name", customer.getName())
                    .param("firstName", customer.getFirstName())
                    .param("passedTheoryExam", customer.getPassedTheoryExam())
                    .update(keyHolder, "id");

            Long generatedId = keyHolder.getKeyAs(Long.class);
            customer.setId(generatedId);

        } else {
            // UPDATE
            jdbcClient.sql("""
                UPDATE customer
                SET name = :name,
                    first_name = :firstName,
                    passed_theory_exam = :passedTheoryExam
                WHERE id = :id
                """)
                    .param("id", customer.getId())
                    .param("name", customer.getName())
                    .param("firstName", customer.getFirstName())
                    .param("passedTheoryExam", customer.getPassedTheoryExam())
                    .update();

            // clear old relationships first, optional???
            jdbcClient.sql("""
                DELETE FROM customer_driving_license
                WHERE customer_id = :id
                """)
                    .param("id", customer.getId())
                    .update();
        }

        // Handle driving licenses
        if (customer.getDrivingLicenses() != null) {
            customer.getDrivingLicenses().forEach(dl -> {
                // Insert into driving_license if this is a new license (no id )
                if (dl.getId() == null) {
                    KeyHolder dlKeyHolder = new GeneratedKeyHolder();

                    jdbcClient.sql("""
                        INSERT INTO driving_license(license, transmission)
                        VALUES (:license, :transmission)
                        """)
                            .param("license", dl.getLicense())
                            .param("transmission", dl.getTransmission()) // assuming String; use .toString() if enum
                            .update(dlKeyHolder, "id");

                    Long dlId = dlKeyHolder.getKeyAs(Long.class);
                    dl.setId(dlId);
                }

                // Insert into customer_driving_license
                jdbcClient.sql("""
                    INSERT INTO customer_driving_license(customer_id, driving_license_id)
                    VALUES (:customerId, :licenseId)
                    """)
                        .param("customerId", customer.getId())
                        .param("licenseId", dl.getId())
                        .update();
            });
        }

        return customer;
    }

    @Override
    public void deleteById(Long id) {
        jdbcClient.sql("""
            DELETE FROM customer_driving_license
            WHERE customer_id = :id
            """)
                .param("id", id)
                .update();

        jdbcClient.sql("""
            DELETE FROM customer_theory_lesson
            WHERE customer_id = :id
            """)
                .param("id", id)
                .update();

        jdbcClient.sql("""
            DELETE FROM customer
            WHERE id = :id
            """)
                .param("id", id)
                .update();
    }

    @Override
    public Optional<Customer> findByFirstNameAndName(String firstName, String name) {
        return jdbcClient.sql("""
            SELECT * FROM customer c
            WHERE c.first_name = :firstName AND c.name = :name
            """)
                .param("firstName", firstName)
                .param("name", name)
                .query(Customer.class)
                .optional();

    }
}
