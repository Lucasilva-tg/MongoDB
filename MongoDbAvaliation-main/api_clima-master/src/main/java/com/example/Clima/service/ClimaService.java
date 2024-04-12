package com.example.Clima.service;

import com.example.Clima.model.ClimaEntity;
import com.example.Clima.repository.ClimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Service
public class ClimaService {

    @Autowired
    private ClimaRepository climaRepository;

    public List<ClimaEntity> obterTodos() {return climaRepository.findAll();}

    public ClimaEntity obterPorId(String id) {return climaRepository.findById(id).orElse(null);}

    public ClimaEntity inserir(ClimaEntity clima) {return climaRepository.save(clima);}

    public void excluir(String id) {climaRepository.deleteById(id);}

    public String preverTempo() {

        String dadosMeterologicos = "";
        String apiUrl = "http://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale/BR?token=8f0b8bf3ee101a723aca7c33719d3d36";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dadosMeterologicos = responseEntity.getBody();
        } else {
            dadosMeterologicos = "Falha ao obter dados meteorológicos. Código de status: " + responseEntity.getStatusCode();
        }
            inserirDados(dadosMeterologicos);
        return dadosMeterologicos;
    }

    public void inserirDados(String dadosMeteorologicos) {
        try {
            JSONArray jsonArray = new JSONArray(dadosMeteorologicos);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    ClimaEntity clima = criarClimaEntityDeJson(jsonObj);
                    climaRepository.save(clima);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ClimaEntity criarClimaEntityDeJson(JSONObject jsonObj) {
        ClimaEntity clima = new ClimaEntity();
        try {
            clima.setPais(jsonObj.getString("country"));
            clima.setData(jsonObj.getString("date"));
            clima.setTexto(jsonObj.getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clima;
    }
}
