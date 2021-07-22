package co.copper.testtask.controller;

import co.copper.testtask.dto.Collateral;
import co.copper.testtask.service.CollateralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CollateralObjectController {
    @Autowired
    private CollateralService service;

    @PostMapping("/collaterals")
    public HttpEntity<Long> save(@RequestBody Collateral object) {
        Long id = service.saveCollateral(object);
        return id != null ? ResponseEntity.ok(id) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/collaterals/{id}")
    public HttpEntity<Collateral> getInfo(@PathVariable String id) {
        Collateral info = service.getInfo(id);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }
}
