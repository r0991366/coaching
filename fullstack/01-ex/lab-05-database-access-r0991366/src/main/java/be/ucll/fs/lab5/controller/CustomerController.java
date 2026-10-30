package be.ucll.fs.lab5.controller;

import be.ucll.fs.lab5.controller.dto.CustomerInput;
import be.ucll.fs.lab5.model.Customer;
import be.ucll.fs.lab5.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @PostMapping
    public Customer create(@Valid @RequestBody CustomerInput input) {
        return customerService.create(input);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        customerService.removeCustomer(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        var errors = new HashMap<String, String>();
        errors.put("error", ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        for (FieldError error : ex.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }
}
