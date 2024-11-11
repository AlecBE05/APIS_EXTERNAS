package com.codigo.ms_openfeign.service;

import com.codigo.ms_openfeign.aggregates.response.ResponseSunat;

public interface EmpresaService {

    ResponseSunat getInfoSunat(String ruc);
}
