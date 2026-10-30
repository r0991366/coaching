package be.ucll.fs.lab6.dirtyread;

import be.ucll.fs.lab6.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DirtyReadService {

    private final CustomerRepository customerRepository;

    public DirtyReadService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public String getCustomerName(Long id) {
        return customerRepository.findById(id).orElseThrow().getName();
    }

    @Transactional
    public void updateCustomerName(Long id) {
        customerRepository.updateCustomerName(id, "Nonsensical Name");

        // Simulate delay before rollback
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Rollback the transaction
        throw new RuntimeException("Name is not nice, rolling back");
    }
}
