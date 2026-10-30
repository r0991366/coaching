package be.ucll.fs.lab6.service;

import be.ucll.fs.lab6.controller.dto.CustomerInput;
import be.ucll.fs.lab6.model.Customer;
import be.ucll.fs.lab6.model.DrivingLicense;
import be.ucll.fs.lab6.repository.CustomerRepository;
import be.ucll.fs.lab6.repository.DrivingLicenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final DrivingLicenseRepository drivingLicenseRepository;

    public CustomerService(CustomerRepository customerRepository, DrivingLicenseRepository drivingLicenseRepository) {
        this.customerRepository = customerRepository;
        this.drivingLicenseRepository = drivingLicenseRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer create(CustomerInput input) {
        var customer = new Customer(input.firstName(), input.name(), input.passedTheoryExam());

        for (var licenseInput : input.licenses()) {
            DrivingLicense license;
            if (licenseInput.id() != null) {
                license = drivingLicenseRepository.findById(licenseInput.id()).orElseThrow();
            } else {
                license = new DrivingLicense(licenseInput.license(), licenseInput.transmission());
                license = drivingLicenseRepository.save(license);
            }

            customer.addDrivingLicense(license);
        }

        if (customerRepository.findByFirstNameAndName(input.firstName(), input.name()).isPresent()) {
            throw new RuntimeException("This customer already exists! Not adding it again to the DB.");
        }

        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void removeFirstDrivingLicenseFromFirstCustomer() {
        customerRepository.findById(1L).orElseThrow().getDrivingLicenses().remove(0);
    }
}
