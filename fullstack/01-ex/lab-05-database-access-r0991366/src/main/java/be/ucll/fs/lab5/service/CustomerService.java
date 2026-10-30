package be.ucll.fs.lab5.service;

import be.ucll.fs.lab5.controller.dto.CustomerInput;
import be.ucll.fs.lab5.controller.dto.DrivingLicenseInput;
import be.ucll.fs.lab5.model.Customer;
import be.ucll.fs.lab5.model.DrivingLicense;
import be.ucll.fs.lab5.repository.CustomerRepository;
import be.ucll.fs.lab5.repository.DrivingLicenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        var customer = new Customer(input.firstName(),input.name(),input.passedTheoryExam());
        if (input.licenses() != null) {
            for (DrivingLicenseInput licInput : input.licenses()) {

                DrivingLicense license;

                if (licInput.id() != null) {
                    // existing license by id
                    license = drivingLicenseRepository.findById(licInput.id())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown license id: " + licInput.id()));
                } else {
                    // new license from fields
                    license = new DrivingLicense(
                            licInput.license(),
                            licInput.transmission()
                    );
                    license = drivingLicenseRepository.save(license);
                }
                customer.addDrivingLicense(license);
            }
        }
        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
