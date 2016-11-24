package smkn4.bdg.sipppid.other;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import smkn4.bdg.sipppid.DAO.Documents;
import smkn4.bdg.sipppid.R;

public class DocumentAdapter extends ArrayAdapter<Documents> {
    ArrayList<Documents> documentList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public DocumentAdapter(Context context, int resource, ArrayList<Documents> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        documentList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.tvId = (TextView) v.findViewById(R.id.tvId);
            holder.tvJudul= (TextView) v.findViewById(R.id.tvJudul);
            holder.tvPublisher= (TextView) v.findViewById(R.id.tvPublisher);
            holder.tvIdJenis = (TextView) v.findViewById(R.id.tvIdJenis);
            holder.tvIdKat = (TextView) v.findViewById(R.id.tvIdKat);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //holder.tvId.setText(documentList.get(position).getId());
        holder.tvJudul.setText(documentList.get(position).getJudul());
        holder.tvPublisher.setText(documentList.get(position).getPublisher());
        holder.tvIdJenis.setText(documentList.get(position).getJenis());
        holder.tvIdKat.setText(documentList.get(position).getKategori());
        return v;

    }

    static class ViewHolder {
        //public TextView tvId;
        public TextView tvJudul;
        public TextView tvPublisher;
        public TextView tvIdJenis;
        public TextView tvIdKat;

    }
}
