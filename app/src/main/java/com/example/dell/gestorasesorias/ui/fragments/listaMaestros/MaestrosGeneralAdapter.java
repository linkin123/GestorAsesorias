package com.example.dell.gestorasesorias.ui.fragments.listaMaestros;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.data.models.Maestro;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 10/06/2018.
 */

public class MaestrosGeneralAdapter extends RecyclerView.Adapter<MaestrosGeneralAdapter.ItemViewHolder> {

    private Context mcontext;
    private List<Maestro> list;
    private onItemClickListener mListener;
    private onLongItemClickListener mLongListener;
    private  MaestrosGeneralAdapter adapter = this;

    public MaestrosGeneralAdapter(Context mcontext, List<Maestro> list, onItemClickListener mListener , onLongItemClickListener mLongListener) {
        this.mcontext = mcontext;
        this.list = list;
        this.mListener = mListener;
        this.mLongListener = mLongListener;

    }

    public void updateList(List<Maestro> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_maestros, parent, false);
        return new ItemViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final Maestro maestro = list.get(position);

        holder.name.setText(maestro.getNombre());
        holder.foto.setImageBitmap(maestro.getBitmap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(maestro);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mostrarAlertEliminar(position);
                mLongListener.onLongClick(maestro.getId());
                return false;
            }
        });


    }

    private void mostrarAlertEliminar(final int position ) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(mcontext);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Eliminar maestro ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
                list.remove(position);
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(position);
                Toast.makeText(mcontext , "elemento eliminado", Toast.LENGTH_LONG);
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CircleImageView foto;

        public ItemViewHolder(View v) {
            super(v);
            name = itemView.findViewById(R.id.tv_name);
            foto = itemView.findViewById(R.id.civ_maestro);
        }

    }

    public interface onItemClickListener {
        void onItemClick(Maestro maestro);
    }

    public interface onLongItemClickListener{
        boolean onLongClick( int id);
    }


}
