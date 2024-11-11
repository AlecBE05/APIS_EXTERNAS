package com.codigo.ms_retrofit.service;

import com.codigo.ms_retrofit.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface EmpresaService {

    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
