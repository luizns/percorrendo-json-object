package com.luiz.percorrerjson;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class PercorrerJsonApplication {

    public static String buscarEndereco(String cep) {
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
            URLConnection urlConnection = url.openConnection();
            InputStream entrada = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(entrada));

            StringBuilder jsonSb = new StringBuilder();
            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }


    public static void main(String[] args)  throws Exception {
        SpringApplication.run(PercorrerJsonApplication.class, args);

        String json = buscarEndereco("01310916");
        System.out.println("Retorno da buscar:  " + json);

        Object obj = new JSONParser().parse(json);

        JSONObject dado = (JSONObject) obj;
        System.out.println();

        Map address = new LinkedHashMap();
        address.put("endereco",dado);

        System.out.println("----------------- Endereço  -------------------");
        System.out.println(address);


        System.out.println();
        System.out.println("--------------Percorrendo Objeto -----------------");
        Map address1 = ((Map)address.get("endereco"));


        Iterator<Map.Entry> iterator = address1.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = iterator.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }

    }

}
