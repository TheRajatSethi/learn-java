package rs.customer.service.coreCustomer.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.customer.service.coreCustomer.service.CoreService;
import rs.customer.service.coreCustomer.service.dto.CustomerDto;
import rs.customer.service.coreCustomer.service.dto.ResponseDto;
import rs.customer.service.coreCustomer.service.dto.StatusDto;


import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("customers")
class CoreController {

    @Autowired
    CoreService service;

    @PostMapping("")
    public ResponseDto<Integer> customerCreate(@RequestBody CustomerDto body){
        int id = service.createCustomer(body);

        StatusDto statusDto = new StatusDto("test", 1, 200, -1, "");
        ResponseDto<Integer> responseDto = new ResponseDto<>(HttpStatus.OK);
        responseDto.setResponse(id);
        responseDto.setCreateSubstatuses(null);
        responseDto.setStatus(statusDto);

        return responseDto;
    }

    @GetMapping("/{int:id}")
    void getCustomerById(@RequestParam Optional<Integer> id){
        log.info("in getCustomerById");
    }

}
