package com.artexceptionals.youreuro.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.model.CashRecord;

import java.util.ArrayList;
import java.util.List;

public class CashRecordAdapter extends RecyclerView.Adapter<CashRecordAdapter.CashRecordViewHolder>{

    private RecordClickListener clickListener;
    private List<CashRecord> recordList = new ArrayList<>();


    @NonNull
    @Override
    public CashRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_cash_record,viewGroup,false);

        return new CashRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashRecordViewHolder cashRecordViewHolder, int i) {

    }

    @Override
    public int getItemCount() {

        if (recordList != null) {
            return recordList.size();
        }
        return 0;
    }

    public class CashRecordViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        public CashRecordViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getLayoutPosition());
        }
    }

    public interface RecordClickListener {
        void onClick(int position);
    }
}
