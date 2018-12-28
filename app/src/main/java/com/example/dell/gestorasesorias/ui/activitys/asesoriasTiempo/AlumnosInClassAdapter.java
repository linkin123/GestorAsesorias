package com.example.dell.gestorasesorias.ui.activitys.asesoriasTiempo;

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
import com.example.dell.gestorasesorias.data.models.Alumno;
import com.example.dell.gestorasesorias.data.models.AlumnoEnClase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlumnosInClassAdapter extends RecyclerView.Adapter<AlumnosInClassAdapter.ItemViewHolder>{


    private Context context;
    private List<AlumnoEnClase> list;
    private onItemClickListener mListener;
    private onLongItemClickListener mLongListener;
    private AlumnosInClassAdapter adapter = this;


    public AlumnosInClassAdapter(Context context, List<AlumnoEnClase> list, onItemClickListener mListener, onLongItemClickListener mLongListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
        this.mLongListener = mLongListener;
    }

    public void updateList(List<AlumnoEnClase> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alumnos_in_class , parent , false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final AlumnoEnClase alumnoEnClase = list.get(position);

        holder.nameAlumno.setText(alumnoEnClase.getAlumno());
        holder.nameProfesor.setText(alumnoEnClase.getMaestro());
        holder.materia.setText(alumnoEnClase.getMateria());
        holder.entrada.setText(alumnoEnClase.getHoraEntrada());
        holder.salida.setText(alumnoEnClase.getHoraSalida());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(alumnoEnClase);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mostrarAlertEliminar(position);
                mLongListener.onLongClick(alumnoEnClase.getId());
                return false;
            }
        });

    }

    private void mostrarAlertEliminar(final int position) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Terminar sesión ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
                list.remove(position);
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(position);
                Toast.makeText(context, "sesión terminada", Toast.LENGTH_LONG);
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

        TextView nameAlumno;
        TextView nameProfesor;
        TextView entrada;
        TextView salida;
        TextView materia;


        public ItemViewHolder(View v) {
            super(v);
            nameAlumno = itemView.findViewById(R.id.tv_item_name_alumno);
            nameProfesor = itemView.findViewById(R.id.tv_item_name_master);
            entrada = itemView.findViewById(R.id.tv_item_entrada);
            salida = itemView.findViewById(R.id.tv_item_salida);
            materia = itemView.findViewById(R.id.tv_item_materia);
        }
    }

    public interface onItemClickListener {
        void onItemClick(AlumnoEnClase alumnoEnClase);
    }

    public interface onLongItemClickListener {
        boolean onLongClick(int id);
    }

}
