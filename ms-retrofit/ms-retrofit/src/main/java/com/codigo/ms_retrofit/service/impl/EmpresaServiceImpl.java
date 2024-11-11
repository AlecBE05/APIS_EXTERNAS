package com.codigo.ms_retrofit.service.impl;

import com.codigo.ms_retrofit.aggregates.response.ResponseSunat;
import com.codigo.ms_retrofit.aggregates.constants.Constans;
import com.codigo.ms_retrofit.entity.EmpresaEntity;
import com.codigo.ms_retrofit.redis.RedisService;
import com.codigo.ms_retrofit.retrofit.ClientSunatService;
import com.codigo.ms_retrofit.retrofit.impl.ClientSunatServiceImpl;
import com.codigo.ms_retrofit.service.EmpresaService;
import com.codigo.ms_retrofit.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;


@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final RedisService redisService;

    ClientSunatService sunatServiceRetrofit = ClientSunatServiceImpl
            .getRetrofit()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc)  throws IOException {

        ResponseSunat datosSunat = new ResponseSunat();

        String redisInfo = redisService.getDataFromRedis(Constans.REDIS_KEY_API_SUNAT+ruc);

        if(Objects.nonNull(redisInfo)){
            System.out.println("Datos encontrados en Redis para el RUC: " + ruc);
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
            return datosSunat;
        }else{
            Call<ResponseSunat> clientRetrofit = prepareSunatRetrofit(ruc);
            Response<ResponseSunat> executeSunat = clientRetrofit.execute();
            if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
                datosSunat = executeSunat.body();
            }
            String dataForRedis = Util.convertirAString(datosSunat);
            redisService.saveInRedis(Constans.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constans.REDIS_TTL);
            return datosSunat;
        }
    }


    private Call<ResponseSunat> prepareSunatRetrofit(String ruc){
        String tokenComplete = Constans.BEARER+token;
        return sunatServiceRetrofit.getInfoSunat(tokenComplete,ruc);
    }

}
