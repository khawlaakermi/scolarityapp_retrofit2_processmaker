package com.example.fragmenttest;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmenttest.Model.Case;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Case> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public interface OnItemClickListener {
        void onItemClick(Case item);
    }
    private final OnItemClickListener listener;
    public RecyclerAdapter(Context context, List<Case> data,OnItemClickListener listener) {
        this.data = data;
        this.layoutInflater=LayoutInflater.from(context);
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view=layoutInflater.inflate(R.layout.recycler_layout,viewGroup,false);
        ViewHolder myview=new ViewHolder(view);
        myview.list_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new SuiviFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.blankFragment, myFragment).addToBackStack(null).commit();

            }
        });
        return  myview;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Case task=data.get(position);
        holder.itemtask.setText(task.getPro_title());

        holder.bind(data.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return  data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {   LinearLayout list_task;
        TextView itemtask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemtask=itemView.findViewById(R.id.row);
            list_task=itemView.findViewById(R.id.list_task);
        }
        public void bind(final Case item, final OnItemClickListener listener) {
           // itemtask.setText(item.getPro_title());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }


    }


}
