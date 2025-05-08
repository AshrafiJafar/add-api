package com.jafar.add_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indicates that this class handles REST requests
public class CalculatorController {

    @Autowired
    private CalculationRepository calculationRepository
    ;
    @PostMapping("/add") // Maps the /add endpoint to the add() method
    public Result add(@RequestParam("num1") int num1, @RequestParam("num2") int num2) {
        var res = new Result();
        res.result = num1 + num2;
        Calculation calculation = new Calculation(num1, num2, res.result);
        calculationRepository.save(calculation); // Save to the database
        return res;
    }

    @GetMapping("/calculations") // New endpoint to get all calculations
    public List<Calculation> getCalculations() {
        return calculationRepository.findAll();
    }

    @GetMapping("/health") // New endpoint to get all calculations
    public int health() {
        return 200;
    }
}
