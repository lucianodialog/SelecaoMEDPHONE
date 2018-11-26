package criawebmobile.com.br.selecaomedphonee.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    public static String getDados(String urlAPI){

        BufferedReader reader = null;

        try {
            URL url = new URL(urlAPI);//Converte a uri em url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//Abre a conexão com a rota que foi passada por parâmetro

            StringBuilder stringBuilder = new StringBuilder();//Armazena cada dado lido pelo reader;
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));//Recupera os dados na forma de bytes através do InputStream e converte os mesmos para caracteres

            String line;//Para ler cada uma da linhas que estão no reader e montar o texto
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");//Armazena todos os dados
            }

            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
