package tr.gen.turkan.customlistview;

import android.graphics.Bitmap;

public class RehberKisiler {
    private String ad_soyad;
    private String tel_no;
    private Bitmap fotograf=null;

    public Bitmap getPhoto() {
        return fotograf;
    }

    public void setPhoto(Bitmap fotograf) {
        this.fotograf = fotograf;
    }

    public String getName()
    {
        return ad_soyad;
    }

    public void setName(String ad_soyad)
    {
        this.ad_soyad = ad_soyad;
    }

    public String getPhoneNumber()
    {
        return tel_no;
    }

    public void setTelNumber(String tel_no)
    {
        this.tel_no = tel_no;
    }
}
