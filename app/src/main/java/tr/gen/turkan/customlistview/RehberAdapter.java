package tr.gen.turkan.customlistview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tr.gen.turkan.customlistview.RehberKisiler;

public class RehberAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<RehberKisiler> KisilerList;

    public RehberAdapter(Activity activity, ArrayList<RehberKisiler> KisilerList) {
        this.mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.KisilerList = KisilerList;
    }

    @Override
    public int getCount() {
        return KisilerList.size();
    }

    @Override
    public Object getItem(int position) {
        return KisilerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //bir görünüm oluşturuyoruz ve buradaki görünümü nereden doldurması gerektiği söylememiz lazım
        convertView = mInflater.inflate(R.layout.rehbertasarim, null); //tasarladığımız layouta bağladık
        TextView kisiad = (TextView) convertView.findViewById(R.id.ad_soyad);
        TextView kisitelno = (TextView) convertView.findViewById(R.id.tel_no);
        ImageView kisiresim = (ImageView) convertView.findViewById(R.id.fotograf);
        RehberKisiler kisi = KisilerList.get(position); //rehberkişilerinden kişi  aldık
        kisiad.setText(kisi.getName());
        kisitelno.setText(kisi.getPhoneNumber());
        if (KisilerList.get(position).getPhoto() != null) //kişinin resmi yoksa boş geleceği için kontrol ediyoruz.
            kisiresim.setImageBitmap(KisilerList.get(position).getPhoto());
        else {
            kisiresim.setImageResource(R.drawable.unknown);
        }
        convertView.setTag(KisilerList.get(position).getName());
        return convertView;
    }
}

