package com.example.dell.gestorasesorias.ui.activitys.ajustes.listaMaterias;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.data.models.Maestro;
import com.example.dell.gestorasesorias.data.models.Materia;
import com.example.dell.gestorasesorias.ui.fragments.listaAlumnos.ListaAlumnosPaqueteFragment;
import com.example.dell.gestorasesorias.ui.fragments.listaMaestros.MaestrosGeneralAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 09/12/2018.
 */

public class ListaMateriasAdapter extends RecyclerView.Adapter<ListaMateriasAdapter.ItemViewHolder>{

    private Context context;
    private List<Materia> list;
    private onItemClickListener mListener;
    private onLongItemClickListener mLongListener;
    private ListaMateriasAdapter adapter = this;

    public ListaMateriasAdapter(Context context, List<Materia> list, onItemClickListener mListener, onLongItemClickListener mLongListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
        this.mLongListener = mLongListener;
    }

    public void updateList(List<Materia> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_maeterias, parent, false);
        return new  ListaMateriasAdapter.ItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final Materia materia = list.get(position);

        holder.name.setText(materia.getNombre());
        holder.foto.setImageBitmap(materia.getBitmap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(materia);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mostrarAlertEliminar(position);
                mLongListener.onLongClick(materia.getId());
                return false;
            }
        });

    }

    private void mostrarAlertEliminar(final int position ) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Eliminar materia ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
                list.remove(position);
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(position);
                Toast.makeText(context , "elemento eliminado", Toast.LENGTH_LONG);
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

    public static class ItemViewHolder extends  RecyclerView.ViewHolder{

        TextView name;
        CircleImageView foto;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            foto = itemView.findViewById(R.id.civ_materia);

        }
    }

    public interface onItemClickListener {
        void onItemClick(Materia materia);
    }

    public interface onLongItemClickListener{
        boolean onLongClick( int id);
    }
}
