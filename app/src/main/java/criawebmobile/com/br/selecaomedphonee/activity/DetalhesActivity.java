package criawebmobile.com.br.selecaomedphonee.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import criawebmobile.com.br.selecaomedphonee.R;
import criawebmobile.com.br.selecaomedphonee.model.Favoritos;

public class DetalhesActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private ValueEventListener valueEventListener;

    private TextView txtDetailId, txtDetailName, txtDetailCreatedAt;
    private ImageView imgDetailAvatar, imgFavorito;
    boolean set_favorito = false;

    private Bitmap detalhe_imgAvatar;
    private String detalhe_id;
    private String detalhe_createAt;
    private String detalhe_name;

    private DatabaseReference db_favoritos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        detalhe_imgAvatar = (Bitmap) intent.getParcelableExtra(MainActivity.KEY_IMG_AVATAR);
        detalhe_id = intent.getStringExtra(MainActivity.KEY_ID);
        detalhe_createAt = intent.getStringExtra(MainActivity.KEY_CREATEDAT);
        detalhe_name = intent.getStringExtra(MainActivity.KEY_NAME);

        //Toast.makeText(this, detalhe_id, Toast.LENGTH_SHORT).show();

        imgDetailAvatar = (ImageView) findViewById(R.id.img_detail_avatar);
        imgFavorito = (ImageView) findViewById(R.id.img_favorito);
        txtDetailName = (TextView) findViewById(R.id.txt_detail_name);
        txtDetailCreatedAt = (TextView) findViewById(R.id.txt_detail_createdAt);
        txtDetailId = (TextView) findViewById(R.id.txt_detail_id);

        imgDetailAvatar.setImageBitmap(detalhe_imgAvatar);
        txtDetailName.setText(detalhe_name);
        txtDetailCreatedAt.setText(detalhe_createAt);
        txtDetailId.setText("Nº " + String.valueOf(detalhe_id));

        db_favoritos = FirebaseDatabase.getInstance().getReference();





        imgFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(set_favorito == false) {
                    imgFavorito.setImageResource(R.drawable.star_checked);
                    addFavorito();
                    set_favorito = true;
                }  else {
                    imgFavorito.setImageResource(R.drawable.star_unchecked);
                    delFavorito(detalhe_id);
                    set_favorito = false;
                }
            }
        });

        listaFavorioId();

    }

//-----------------------------------Lista Favoritos----------------------------------------------------
   public void listaFavorioId() {


   }


    public void addFavorito() {


            //***************Usando o realtime database Firebase****************************************

            DatabaseReference favoritos = db_favoritos.child("favoritos");
            Favoritos dados_favoritos = new Favoritos();//objeto para se armazenado no realtime Firebase

            dados_favoritos.setId_favorito(Integer.parseInt(detalhe_id));
            dados_favoritos.setCreatedAt_favorito((detalhe_createAt));
            dados_favoritos.setName_favorito((detalhe_name));
            dados_favoritos.setFavorito(true);
            
            favoritos.child(detalhe_id).setValue(dados_favoritos);//Envia os dados para o bd_favoritos

            //***************Usando para buscar apenas os favoritos****************************************


    }


    public void delFavorito(String id_favorito) {

        DatabaseReference favoritos = db_favoritos.child("favoritos").child(id_favorito);
        favoritos.removeValue();

    }
}
