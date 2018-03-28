package deu.hlju.dawn.studentattendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.Student;

public class ShowStudentAdapter extends RecyclerView.Adapter<ShowStudentAdapter.ShowStudentViewHolder> {

    private Context context;
    private List<Student> students;

    public ShowStudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    public void refresh(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @Override
    public ShowStudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowStudentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_show_student, parent, false));
    }

    @Override
    public void onBindViewHolder(ShowStudentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Student student = students.get(position);
        holder.position = position;
        holder.nameTv.setText(student.getName());
        holder.idTv.setText(student.getId());
        Picasso.with(context).load(student.getPortrait()).into(holder.portraitCiv);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class ShowStudentViewHolder extends RecyclerView.ViewHolder {

        int position;
        TextView nameTv;
        TextView idTv;
        CircleImageView portraitCiv;

        public ShowStudentViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            idTv = itemView.findViewById(R.id.id_tv);
            portraitCiv = itemView.findViewById(R.id.portrait_civ);
        }
    }
}
