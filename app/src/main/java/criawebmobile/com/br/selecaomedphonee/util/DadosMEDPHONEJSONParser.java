package criawebmobile.com.br.selecaomedphonee.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import criawebmobile.com.br.selecaomedphonee.model.DadosMEDPHONE;

public class DadosMEDPHONEJSONParser {

    public static List<DadosMEDPHONE> parseDados(String content) {
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<DadosMEDPHONE> dadosMEDPHONEList = new ArrayList<>();

            for (int i = 0; i< jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DadosMEDPHONE dadosMEDPHONE = new DadosMEDPHONE();

                dadosMEDPHONE.setId(jsonObject.getInt("id"));
                dadosMEDPHONE.setCreatedAt(jsonObject.getString("createdAt"));
                dadosMEDPHONE.setName(jsonObject.getString("name"));
                dadosMEDPHONE.setAvatar(jsonObject.getString("avatar"));

                dadosMEDPHONEList.add(dadosMEDPHONE);
            }

            return dadosMEDPHONEList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
