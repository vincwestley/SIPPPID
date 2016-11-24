package smkn4.bdg.sipppid.other;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import smkn4.bdg.sipppid.DAO.Documents;
import smkn4.bdg.sipppid.DAO.KeberatanDao;
import smkn4.bdg.sipppid.R;

public class KeberatanAdapter extends ArrayAdapter<KeberatanDao> {
    ArrayList<KeberatanDao> keberatanList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public KeberatanAdapter(Context context, int resource, ArrayList<KeberatanDao> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        keberatanList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.tvId = (TextView) v.findViewById(R.id.tvId);
            holder.tvPermohonan= (TextView) v.findViewById(R.id.tvPermohonan);
            holder.tvReferensi= (TextView) v.findViewById(R.id.tvReferensi);
            holder.tvTanggal= (TextView) v.findViewById(R.id.tvTanggal);
            holder.tvStatus= (TextView) v.findViewById(R.id.tvStatus);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //holder.tvId.setText(documentList.get(position).getId());
        holder.tvPermohonan.setText(keberatanList.get(position).getPermohonan());
        holder.tvReferensi.setText(keberatanList.get(position).getRefferensi());
        holder.tvTanggal.setText(keberatanList.get(position).getTanggal());
        holder.tvStatus.setText(keberatanList.get(position).getStatus());/*
        holder.tvIdKat.setText(keberatanList.get(position).getKategori());*/
        return v;

    }

    static class ViewHolder {
        //public TextView tvId;
        public TextView tvPermohonan;
        public TextView tvReferensi;
        public TextView tvTanggal;
        public TextView tvStatus;

    }
}
