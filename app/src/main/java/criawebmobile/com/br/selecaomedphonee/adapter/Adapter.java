package criawebmobile.com.br.selecaomedphonee.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import criawebmobile.com.br.selecaomedphonee.model.DadosMEDPHONE;
import criawebmobile.com.br.selecaomedphonee.R;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<DadosMEDPHONE> listaDadosMEDPHONE;

    public Adapter(List<DadosMEDPHONE> lista) {

        listaDadosMEDPHONE = lista;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_lista, viewGroup, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        DadosMEDPHONE dadosMEDPHONE2 = listaDadosMEDPHONE.get(position);

        myViewHolder.id.setText(String.valueOf(dadosMEDPHONE2.getId()));
        myViewHolder.createdAt.setText(dadosMEDPHONE2.getCreatedAt());
        myViewHolder.name.setText(dadosMEDPHONE2.getName());
        myViewHolder.avatar.setText(dadosMEDPHONE2.getAvatar());
        myViewHolder.imgAvatar.setImageBitmap(dadosMEDPHONE2.getImgAvatar());
    }

    @Override
    public int getItemCount() {
        return listaDadosMEDPHONE.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

       TextView id;
       TextView createdAt;
       TextView name;
       TextView avatar;
       ImageView imgAvatar;

       public MyViewHolder(View itemView) {
           super(itemView);

           id = itemView.findViewById(R.id.txt_id);
           createdAt = itemView.findViewById(R.id.txt_createdAt);
           name = itemView.findViewById(R.id.txt_name);
           avatar = itemView.findViewById(R.id.txt_avatar);
           imgAvatar = itemView.findViewById(R.id.img_avatar);

       }
   }


}
