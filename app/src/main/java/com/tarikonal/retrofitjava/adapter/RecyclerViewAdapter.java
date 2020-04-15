package com.tarikonal.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tarikonal.retrofitjava.R;
import com.tarikonal.retrofitjava.model.CyrptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<CyrptoModel> cyrptoList;
    private String[] colors={"#3cff3c","#ffc5c8","#4285f4","#eec1ad","#0099cc","#fcd116","#bbbbbb","#ff965b"};
    public RecyclerViewAdapter(ArrayList<CyrptoModel> cyrptoList) {
        this.cyrptoList = cyrptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
holder.bind(cyrptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cyrptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);


        }
        public void bind(CyrptoModel cyrptoModel, String[] colors, Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
            textName = itemView.findViewById(R.id.text_name);
            textPrice = itemView.findViewById(R.id.text_price);
            textName.setText(cyrptoModel.currency);
            textPrice.setText(cyrptoModel.price);
        }
    }
}
