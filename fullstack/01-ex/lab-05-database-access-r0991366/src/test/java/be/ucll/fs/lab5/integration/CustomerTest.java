package be.ucll.fs.lab5.integration;

import be.ucll.fs.lab5.repository.CustomerRepository;
import be.ucll.fs.lab5.repository.DrivingLicenseRepository;
import be.ucll.fs.lab5.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class CustomerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private DrivingLicenseRepository drivingLicenseRepository;

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    public void givenValidCustomerInformationWithoutDrivingLicenses_whenCreateCustomer_thenCustomerIsCreated() {
        client
                .post()
                .uri("/customers")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "firstName": "Josh",
                            "name": "Carlson",
                            "passedTheoryExam": true
                        }
                        """)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .json("""
                      {
                        "id": 7,
                        "firstName": "Josh",
                        "name": "Carlson",
                        "passedTheoryExam": true
                      }
                      """);

        final var customer = customerRepository.findByFirstNameAndName("Josh", "Carlson").orElseThrow();
        Assertions.assertEquals(7L, customer.getId());
    }

    @Test
    public void whenGetCustomers_thenAllCustomersAreReturned() {
        client
                .get()
                .uri("/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                // Missing elements are not matched, so we are essentially checking that the response
                // contains ids 1 till 6 below
                .json("""
                      [
                        {
                          "id": 1
                        },
                        {
                          "id": 2
                        },
                        {
                          "id": 3
                        },
                        {
                          "id": 4
                        },
                        {
                          "id": 5
                        },
                        {
                          "id": 6
                        }
                      ]
                      """);
    }

    @Test
    public void givenValidCustomerId_whenDeleteCustomer_thenCustomerIsDeletedButLessonsAndDrivingLicensesAreNot() {
        client
                .delete()
                .uri("/customers/1")
                .exchange()
                .expectStatus().is2xxSuccessful();

        final var customers = customerRepository.findAll();

        // 6 customers are created in schema.sql, 5 should remain
        Assertions.assertEquals(5, customers.size());

        // The customer with ID 1 should no longer be present
        Assertions.assertTrue(customers.stream().filter(customer -> customer.getId() == 1L).findAny().isEmpty());

        // When a customer is deleted, the entities it has a relationship with SHOULD NOT BE deleted
        // For customer 1, this is driving license 1 and lessons 1 and 5 (see schema.sql)
        Assertions.assertTrue(drivingLicenseRepository.findById(1L).isPresent());
        Assertions.assertTrue(lessonRepository.findById(1L).isPresent());
        Assertions.assertTrue(lessonRepository.findById(5L).isPresent());

        // When a customer is deleted, its relationships with other entities SHOULD BE deleted
        Assertions.assertEquals(jdbcClient.sql("select count(*) from customer_driving_license where customer_id = 1").query(Integer.class).single(), 0);
        Assertions.assertEquals(jdbcClient.sql("select count(*) from customer_theory_lesson where customer_id = 1").query(Integer.class).single(), 0);
    }

      // Uncomment when instructed in the lab
    @Test
    public void givenValidCustomerInformation_whenCreateCustomerWithExistingLicenses_thenCustomerIsCreatedWithRelationshipToLicenses() {
        client
                .post()
                .uri("/customers")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "firstName": "Josh",
                            "name": "Carlson",
                            "passedTheoryExam": true,
                            "licenses": [
                              {
                                "id": 1
                              }
                            ]
                        }
                        """)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .json("""
                      {
                        "id": 7,
                        "firstName": "Josh",
                        "name": "Carlson",
                        "passedTheoryExam": true,
                        "drivingLicenses": [
                          {
                            "id":1,
                            "license":"B",
                            "transmission":"Manual"
                          }
                        ]
                      }
                      """);

        final var customer = customerRepository.findByFirstNameAndName("Josh", "Carlson").orElseThrow();
        Assertions.assertEquals(7L, customer.getId());
        Assertions.assertEquals( 1, customer.getDrivingLicenses().size());
        Assertions.assertEquals( 1L, customer.getDrivingLicenses().get(0).getId());
    }

    @Test
    public void givenValidCustomerInformation_whenCreateCustomerWithNewLicenses_thenLicenseIsCreatedAndCustomerIsCreatedWithRelationshipToThatLicense() {
        client
                .post()
                .uri("/customers")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "firstName": "Josh",
                            "name": "Carlson",
                            "passedTheoryExam": true,
                            "licenses": [
                              {
                                "license": "B",
                                "transmission": "Manual"
                              }
                            ]
                        }
                        """)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .json("""
                      {
                        "id": 7,
                        "firstName": "Josh",
                        "name": "Carlson",
                        "passedTheoryExam": true,
                        "drivingLicenses": [
                          {
                            "id":4,
                            "license":"B",
                            "transmission":"Manual"
                          }
                        ]
                      }
                      """);

        final var license = drivingLicenseRepository.findById(4L).orElseThrow();
        Assertions.assertEquals("B", license.getLicense());
        Assertions.assertEquals("Manual", license.getTransmission());

        final var customer = customerRepository.findByFirstNameAndName("Josh", "Carlson").orElseThrow();
        Assertions.assertEquals(7L, customer.getId());
        Assertions.assertEquals( 1, customer.getDrivingLicenses().size());
        Assertions.assertEquals( 4L, customer.getDrivingLicenses().get(0).getId());
    }
}
