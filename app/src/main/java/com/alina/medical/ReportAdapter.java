package com.alina.medical;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ulan on 5/22/17.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private ArrayList<Appointment> records;

    public ReportAdapter(ArrayList<Appointment> records) {
        this.records = records;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_item, viewGroup, false);
        return new ViewHolder(v);
    }



    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Appointment record = records.get(i);
        viewHolder.name.setText(record.getName());
        viewHolder.phone.setText(record.getPhone());
        viewHolder.time.setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,phone,time;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            time = (TextView) itemView.findViewById(R.id.time_ff);


        }
    }
}
