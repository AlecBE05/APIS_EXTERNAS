package com.codigo.ms_resttemplate.controller;


import com.codigo.ms_resttemplate.aggregates.response.ResponseSunat;
import com.codigo.ms_resttemplate.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/sunat/{ruc}")
    public ResponseEntity<ResponseSunat> getInfoSunat(
            @PathVariable String ruc) {
        return new ResponseEntity<>(empresaService.getInfoSunat(ruc), HttpStatus.OK);
    }
}
