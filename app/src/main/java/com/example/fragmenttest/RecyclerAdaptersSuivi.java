package com.example.fragmenttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmenttest.Model.Process;

import java.util.List;

public class RecyclerAdaptersSuivi extends  RecyclerView.Adapter<RecyclerAdaptersSuivi.ViewHolder> {
    private List<Process> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public RecyclerAdaptersSuivi(Context context, List<Process> data)
    { this.context=context;
      this.data=data;
      this.layoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerAdaptersSuivi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.recycler_layout_suivi,parent,false);
        RecyclerAdaptersSuivi.ViewHolder myview=new RecyclerAdaptersSuivi.ViewHolder(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptersSuivi.ViewHolder holder, int position) {
        Process task=data.get(position);
        holder.id.setText(task.getApp_number()+"");
        holder.process.setText(task.getApp_pro_title());
        holder.status.setText(task.getApp_status());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout list_task;
        TextView id;
        TextView process;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.number);
            process=itemView.findViewById(R.id.process);
            status=itemView.findViewById(R.id.status);
            list_task=itemView.findViewById(R.id.list_task1);
        }
    }

    public void addprocess(List<Process> process)
    {

        for (Process p :process)
        {
            data.add(p);
        }
    }
}
