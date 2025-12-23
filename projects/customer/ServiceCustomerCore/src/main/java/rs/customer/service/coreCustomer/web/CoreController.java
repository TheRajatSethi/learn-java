package rs.customer.service.coreCustomer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("customers")
class CoreController {

    @Autowired
    CoreService service;

    @PostMapping("")
    public ResponseEntity<String> customerCreate(@RequestBody CustomerDto body){
        service.createCustomer(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{int:id}")
    void getCustomerById(@RequestParam Optional<Integer> id){
        log.info("in getCustomerById");
    }

}
