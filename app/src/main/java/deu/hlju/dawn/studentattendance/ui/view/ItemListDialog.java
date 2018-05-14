package deu.hlju.dawn.studentattendance.ui.view;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;

public class ItemListDialog {

    public static AlertDialog create(Context context, List<String> data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ItemListAdapter(context, data));
        builder.setView(recyclerView);
        builder.setNegativeButton(context.getString(R.string.confirm), null);
        return builder.create();
    }

    private static class ItemListAdapter extends RecyclerView.Adapter<ItemListViewHolder>{

        private Context context;
        private List<String> data;

        public ItemListAdapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public ItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemListViewHolder(new TextView(context));
        }

        @Override
        public void onBindViewHolder(ItemListViewHolder holder, int position) {
            ((TextView)holder.itemView).setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private static class ItemListViewHolder extends RecyclerView.ViewHolder {

        ItemListViewHolder(View itemView) {
            super(itemView);
        }
    }

}
