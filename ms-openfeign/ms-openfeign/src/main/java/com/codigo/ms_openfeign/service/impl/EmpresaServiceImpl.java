package com.codigo.ms_openfeign.service.impl;

import com.codigo.ms_openfeign.aggregates.constans.Constants;
import com.codigo.ms_openfeign.aggregates.response.ResponseSunat;
import com.codigo.ms_openfeign.client.ClientSunat;
import com.codigo.ms_openfeign.entity.EmpresaEntity;
import com.codigo.ms_openfeign.redis.RedisService;
import com.codigo.ms_openfeign.repository.EmpresaReposiroty;
import com.codigo.ms_openfeign.service.EmpresaService;
import com.codigo.ms_openfeign.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final ClientSunat clientSunat;
    private final RedisService redisService;


    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl( ClientSunat clientSunat, RedisService redisService) {
        this.clientSunat = clientSunat;
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc) {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        ResponseSunat datosSunat = new ResponseSunat();

        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT+ruc);

        if(Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
            return datosSunat;
        }else{
            datosSunat = executionSunat(ruc);
            String dataForRedis = Util.convertirAString(datosSunat);
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
            return datosSunat;
        }
    }

    private ResponseSunat executionSunat(String ruc){
        String tokenOk = Constants.BEARER+token;
        return clientSunat.getEmpresaSunat(ruc,tokenOk);
    }
}
