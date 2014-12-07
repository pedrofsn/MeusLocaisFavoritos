package br.pedrofsn.meuslocaisfavoritos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.model.Local;

/**
 * Created by pedrofsn on 06/12/2014.
 */
public class AdapterLocal extends RecyclerView.Adapter<AdapterLocal.ViewHolder> {

    private final List<Local> listLocals;

    public AdapterLocal(List<Local> list) {
        listLocals = list;
    }

    @Override
    public AdapterLocal.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_local, parent, false);

        ViewHolder holder = new ViewHolder(v);
        holder.textViewNome.setTag(holder);
        holder.textViewEndereco.setTag(holder);
        holder.textViewLatitude.setTag(holder);
        holder.textViewLongitude.setTag(holder);
        holder.textViewHorario.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewNome.setText(listLocals.get(position).getNome());
        holder.textViewEndereco.setText(listLocals.get(position).getEndereco());
        holder.textViewLatitude.setText(String.valueOf(listLocals.get(position).getLatitude()));
        holder.textViewLongitude.setText(String.valueOf(listLocals.get(position).getLongitude()));
        holder.textViewHorario.setText(listLocals.get(position).getDataDoCheckin().toString());
    }

    @Override
    public int getItemCount() {
        return listLocals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textViewNome;
        public final TextView textViewEndereco;
        public final TextView textViewLatitude;
        public final TextView textViewLongitude;
        public final TextView textViewHorario;

        public ViewHolder(View v) {
            super(v);
            textViewNome = (TextView) v.findViewById(R.id.textViewNome);
            textViewEndereco = (TextView) v.findViewById(R.id.textViewEndereco);
            textViewLatitude = (TextView) v.findViewById(R.id.textViewLatitude);
            textViewLongitude = (TextView) v.findViewById(R.id.textViewLongitude);
            textViewHorario = (TextView) v.findViewById(R.id.textViewHorario);
        }
    }
}