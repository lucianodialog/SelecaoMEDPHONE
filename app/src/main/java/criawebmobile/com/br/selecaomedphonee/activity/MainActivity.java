package criawebmobile.com.br.selecaomedphonee.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import criawebmobile.com.br.selecaomedphonee.R;
import criawebmobile.com.br.selecaomedphonee.util.RecyclerItemClickListener;
import criawebmobile.com.br.selecaomedphonee.adapter.Adapter;
import criawebmobile.com.br.selecaomedphonee.model.Bd_MEDPHONE;
import criawebmobile.com.br.selecaomedphonee.model.DadosMEDPHONE;
import criawebmobile.com.br.selecaomedphonee.util.DadosMEDPHONEJSONParser;
import criawebmobile.com.br.selecaomedphonee.util.HttpManager;

public class MainActivity extends AppCompatActivity {

    private TextView txtStatus;
    private Button btnCarregaDados, btnDeletar;
    private ProgressBar progressBar;
    private List<DadosMEDPHONE> dadosMEDPHONEList;
    private RecyclerView recyclerViewDadosMEDPHONE;
    Adapter adapter;

    public static final String KEY_ID = "id";
    public static final String KEY_CREATEDAT = "createdAt";
    public static final String KEY_NAME = "name";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_IMG_AVATAR = "imgAvatar";





    private Bd_MEDPHONE bd_medphone;


    private LruCache<Integer, Bitmap> imgCache;//Para o cache de imagem onde o valor Intenger receberá o id para associar a imagem correspondente


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = (TextView) findViewById(R.id.txt_status);
        btnCarregaDados = (Button) findViewById(R.id.btn_carrega_dados);
        btnDeletar = (Button) findViewById(R.id.btn_deletar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerViewDadosMEDPHONE = (RecyclerView) findViewById(R.id.recyclerViewDadosMEDPHONE);
        dadosMEDPHONEList = new ArrayList<>();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);//Memória máxima em kbs.O cast int é oara garantir o retorno de um int pq a variável maxMemory é int.
        int cacheSize = maxMemory / 8;//Transforma em bytes.

        imgCache = new LruCache<>(cacheSize);//Inicializa minha LruCache
        bd_medphone = new Bd_MEDPHONE(this);


        if(isOnLine()) {
            buscarDados("http://5bf57c322a6f080013a34eaf.mockapi.io/api/v1/entity/");
        } else {

            Toast.makeText(getApplicationContext(), "Rede não está disponível", Toast.LENGTH_LONG).show();

        }


        btnCarregaDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnLine()) {
                   String urlAPI = "http://5bf57c322a6f080013a34eaf.mockapi.io/api/v1/entity/";
                    buscarDados(urlAPI);
                } else {

                    Toast.makeText(getApplicationContext(), "Rede não está disponível", Toast.LENGTH_LONG).show();

                }

            }
        });



        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd_medphone.deletar();
                //Passado true para exibir os dados da lista
                carregaView(true);
            }
        });





    }//Fim onCreate()


    public void onDestroy() {
        super.onDestroy();

        bd_medphone.deletar();

    }



    private void buscarDados(String uri) {
        dadosMEDPHONEList = bd_medphone.findAll();
        Toast.makeText(getApplicationContext(), "O tamanho do cache é = " + imgCache.size(), Toast.LENGTH_SHORT).show();


        if (dadosMEDPHONEList == null || dadosMEDPHONEList.size() == 0) {
            MyTask task = new MyTask();
            task.execute(uri);
        } else{
                //
                for (int i = 0; i < dadosMEDPHONEList.size(); i++) {
                    if (dadosMEDPHONEList.get(i).getId() == dadosMEDPHONEList.get(i).getId()) {
                         Bitmap bitmap = imgCache.get(dadosMEDPHONEList.get(i).getId());
                         dadosMEDPHONEList.get(i).setImgAvatar(bitmap);
                        Log.i("CACHEID", dadosMEDPHONEList.get(i).getId() + "  " + dadosMEDPHONEList.get(i).getName());
                    }
                }




            //Passado false para exibir a lista vazia
            carregaView(false);
            Toast.makeText(getApplicationContext(), "Dados carregados a partir do Banco de dados de cache.", Toast.LENGTH_SHORT).show();
        }

    }//Fim buscarDados

    private boolean isOnLine() {

        ConnectivityManager conectivyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conectivyManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){

            return true;

        } else {

            return false;
        }

     }//Fim isOnline


    public void carregaView(boolean update) {

        adapter = new Adapter(dadosMEDPHONEList);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDadosMEDPHONE.setLayoutManager(layoutManager);
        recyclerViewDadosMEDPHONE.setHasFixedSize(true);

        if (update) {
            recyclerViewDadosMEDPHONE.setAdapter(null);
        } else {
            recyclerViewDadosMEDPHONE.setAdapter(adapter);
        }


        //evento de click
        recyclerViewDadosMEDPHONE.addOnItemTouchListener(

                new RecyclerItemClickListener(

                        getApplicationContext(),
                        recyclerViewDadosMEDPHONE,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                DadosMEDPHONE detalhes_medphone = dadosMEDPHONEList.get(position);
                               // Toast.makeText(getApplicationContext(), "Clicou em " + detalhes_medphone.getName(), Toast.LENGTH_SHORT).show();

                                Intent detalhes_activity = new Intent(view.getContext(), DetalhesActivity.class);
                                detalhes_activity.putExtra(KEY_ID, String.valueOf(detalhes_medphone.getId()));
                                //Toast.makeText(getApplicationContext(), "está passando " + detalhes_medphone.getId(), Toast.LENGTH_SHORT).show();
                                detalhes_activity.putExtra(KEY_CREATEDAT, detalhes_medphone.getCreatedAt());
                                detalhes_activity.putExtra(KEY_NAME, detalhes_medphone.getName());
                                detalhes_activity.putExtra(KEY_AVATAR, detalhes_medphone.getAvatar());
                                detalhes_activity.putExtra(KEY_IMG_AVATAR, detalhes_medphone.getImgAvatar());



                                view.getContext().startActivity(detalhes_activity);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //DadosMEDPHONE dados = dadosMEDPHONEList.get(position);
                                //Toast.makeText(getApplicationContext(), "Clicou em " + dados.getAvatar(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )

        );

    }

//*****************************MyTask************************************************************************************

    private class MyTask extends AsyncTask<String, Integer, List<DadosMEDPHONE>> {

        @Override
        protected void onPreExecute() {
          progressBar.setVisibility(View.VISIBLE);
        }//Fim onPreExecute

        @Override
        protected List<DadosMEDPHONE> doInBackground(String... params) {
            String conteudo = HttpManager.getDados(params[0]);//Recede os dados consumidos da api
            dadosMEDPHONEList = DadosMEDPHONEJSONParser.parseDados(conteudo);//Faz o parse dos dados convertendo-os de uma JSON para String

            int i = 1;
            double x = dadosMEDPHONEList.size();
            double y;






            //Laço para adicionar a imagem aos meu dados
            for (DadosMEDPHONE dadosMEDPHONE : dadosMEDPHONEList) {

                try {

                    Bitmap bitmapCache = imgCache.get(dadosMEDPHONE.getId());
                    //Verifica se a imagem existe no cache. Se não exister faz o download da mesma.
                    if (bitmapCache != null ) {
                        dadosMEDPHONE.setImgAvatar(bitmapCache);
                        Log.i("CACHE", "Usou a imagem do cache");
                    } else {

                        String imgURLAvatar = dadosMEDPHONE.getAvatar();//A String imgURLAvatar recebe a url da qual será baixada a imagem
                        InputStream inputStream = (InputStream) new URL(imgURLAvatar).getContent();//Faz o dawnload da imagem
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//Converte a imagem para Bitmap
                        dadosMEDPHONE.setImgAvatar(bitmap);//Insere a imagem nos meus dados
                        inputStream.close();//Fecha o InputStream
                        imgCache.put(dadosMEDPHONE.getId(), bitmap);
                        Log.i("CACHE", "Baixou a imagem");


                        //Lógica para o progressBar exibir o progresso corretamente
                        progressBar.setMax(dadosMEDPHONEList.size());//Seta o máximo valor do progressBar com o total de itens na minha lista(100%);
                        progressBar.setProgress(i);//Carraga o progressBar a medida que vai setando os dados da imagem.
                        Log.i("PROGRESS", i / (double)x * 100 + "%");
                        y = i / (double)x * 100;//Percentual de um número baseado em outro. Para exibir o percentual ja carregado. Usar o cast (double), se não será sempre zero.
                        publishProgress((int)y);//Passa o percentual para o método que atualiza a view. O cast para garantir um percentual inteiro.
                        i++;//incrementa a variável



                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return dadosMEDPHONEList;//Retorna a lista de dados preenchida.
        }//Fim doInBackground


        @Override
        protected void onPostExecute(List<DadosMEDPHONE> s) {
            progressBar.setVisibility(View.INVISIBLE);

            for (DadosMEDPHONE dadosMEDPHONE: s) {

                bd_medphone.save(dadosMEDPHONE);
            }

            //Passado true para exibir os dados
            carregaView(false);



        }//Fim onPostExecute()


            @Override
            protected void onProgressUpdate(Integer... values) {

                txtStatus.setText(String.valueOf(values[0])  + "%");
                Log.i("CACHEPROGRESS", "PROGRESS... " + values[0]);
            }



    }//Fim AsyncTask
}//Fim MainActivity
