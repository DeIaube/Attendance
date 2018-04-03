package deu.hlju.dawn.studentattendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.AttendanceRecode;

public class ShowRecodeAdapter extends RecyclerView.Adapter<ShowRecodeAdapter.ShowRecodeViewHolder> {

    private Context context;
    private List<AttendanceRecode> attendanceRecodes;

    public ShowRecodeAdapter(Context context, List<AttendanceRecode> attendanceRecodes) {
        this.context = context;
        this.attendanceRecodes = attendanceRecodes;
    }

    public void refresh(List<AttendanceRecode> attendanceRecodes) {
        this.attendanceRecodes = attendanceRecodes;
        notifyDataSetChanged();
    }

    @Override
    public ShowRecodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowRecodeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_show_recode, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ShowRecodeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AttendanceRecode recode = attendanceRecodes.get(position);
        holder.position = position;
        holder.idTv.setText(recode.getStudent().getId());
        holder.nameTv.setText(recode.getStudent().getName());
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH时mm分");
        holder.timeTv.setText(sdf.format(recode.getUpdatedAt()));
        Picasso.with(context).load(recode.getStudent().getPortrait()).into(holder.portraitCiv);
    }

    @Override
    public int getItemCount() {
        return attendanceRecodes.size();
    }

    class ShowRecodeViewHolder extends RecyclerView.ViewHolder {

        int position;
        TextView nameTv;
        TextView idTv;
        TextView timeTv;
        CircleImageView portraitCiv;

        ShowRecodeViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            idTv = itemView.findViewById(R.id.id_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
            portraitCiv = itemView.findViewById(R.id.portrait_civ);
        }
    }
}
