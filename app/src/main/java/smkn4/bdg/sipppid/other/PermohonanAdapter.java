package smkn4.bdg.sipppid.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import smkn4.bdg.sipppid.DAO.PermohonanDao;
import smkn4.bdg.sipppid.R;

/**
 * Created by vincwestley on 17/10/16.
 */
public class PermohonanAdapter extends ArrayAdapter<PermohonanDao> {
    ArrayList<PermohonanDao> permohonanList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public PermohonanAdapter(Context context, int resource, ArrayList<PermohonanDao> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        permohonanList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.tvId = (TextView) v.findViewById(R.id.tvId);
            holder.tvJudul = (TextView) v.findViewById(R.id.tvJudul);
            holder.tvNomor = (TextView) v.findViewById(R.id.tvNomor);
            holder.tvTanggal = (TextView) v.findViewById(R.id.tvTanggal);
            holder.tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //holder.tvId.setText(documentList.get(position).getId());
        holder.tvJudul.setText(permohonanList.get(position).getJudul());
        holder.tvNomor.setText(permohonanList.get(position).getNomor());
        holder.tvTanggal.setText(permohonanList.get(position).getTanggal());
        holder.tvStatus.setText(permohonanList.get(position).getStatus());
        return v;

    }

    static class ViewHolder {
        //public TextView tvId;
        public TextView tvJudul;
        public TextView tvNomor;
        public TextView tvDeskripsi;
        public TextView tvTujuan;
        public TextView tvKomponen;
        public TextView tvStatus;
        public TextView tvTanggal;

    }
}
