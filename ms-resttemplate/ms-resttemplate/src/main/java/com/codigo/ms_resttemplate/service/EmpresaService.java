package com.codigo.ms_resttemplate.service;

import com.codigo.ms_resttemplate.aggregates.response.ResponseSunat;

public interface EmpresaService {

    ResponseSunat getInfoSunat(String ruc);

}
