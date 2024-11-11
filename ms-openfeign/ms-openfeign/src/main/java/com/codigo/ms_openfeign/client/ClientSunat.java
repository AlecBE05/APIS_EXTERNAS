package com.codigo.ms_openfeign.client;


import com.codigo.ms_openfeign.aggregates.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-sunat", url = "https://api.apis.net.pe/v2/sunat/ruc/")
public interface ClientSunat {

    @GetMapping("/full")
    ResponseSunat getEmpresaSunat(@RequestParam("numero") String numero,
                                  @RequestHeader("Authorization") String Authorization);

}
