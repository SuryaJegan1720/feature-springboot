package com.example.demo.Controller;

// import com.example.transactiondemo.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Services.TransferService;


@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public String transfer(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam Double amount) {

        transferService.transfer(
                from,
                to,
                amount);

        return "Transfer completed";
    }

    @GetMapping()
    public String getHi() {
        return "Hi";
    }
    

}