package criawebmobile.com.br.selecaomedphonee.model;

import android.graphics.Bitmap;

public class DadosMEDPHONE {

    private int id;
    private String createdAt;
    private String name;
    private String avatar;
    private Bitmap imgAvatar;

    public Bitmap getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(Bitmap imgAvatar) {
        this.imgAvatar = imgAvatar;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
