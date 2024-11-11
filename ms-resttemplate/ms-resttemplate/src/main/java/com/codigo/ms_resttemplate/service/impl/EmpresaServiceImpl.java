package com.codigo.ms_resttemplate.service.impl;

import com.codigo.ms_resttemplate.aggregates.constans.Constans;
import com.codigo.ms_resttemplate.aggregates.response.ResponseSunat;
import com.codigo.ms_resttemplate.entity.EmpresaEntity;
import com.codigo.ms_resttemplate.redis.RedisService;
import com.codigo.ms_resttemplate.service.EmpresaService;
import com.codigo.ms_resttemplate.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final RestTemplate restTemplate;
    private final RedisService redisService;


    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc) {
        EmpresaEntity personaNaturalEntity = new EmpresaEntity();
        ResponseSunat datosSunat = new ResponseSunat();

        String redisInfo = redisService.getDataFromRedis(Constans.REDIS_KEY_API_SUNAT+ruc);

        if(Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
            return datosSunat;
        }else{
            datosSunat = executeRestTemplate(ruc);
            String dataForRedis = Util.convertirAString(datosSunat);
            redisService.saveInRedis(Constans.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constans.REDIS_TTL);
            return datosSunat;
        }
    }

    private ResponseSunat executeRestTemplate(String ruc){

        String url = "https://api.apis.net.pe/v2/sunat/ruc/full?numero="+ruc;
        ResponseEntity<ResponseSunat> executeRestTemplate = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaderss()),
                ResponseSunat.class
        );
        if(executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        }else{
            return null;
        }
    }

    private HttpHeaders createHeaderss(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Constans.BEARER+token);
        return headers;
    }
}
