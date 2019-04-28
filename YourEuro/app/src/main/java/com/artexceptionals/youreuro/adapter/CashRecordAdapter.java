package com.artexceptionals.youreuro.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.model.CashRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public void onBindViewHolder(@NonNull CashRecordViewHolder holder, int index) {

        if (holder != null){
            CashRecord cashRecord =  recordList.get(index);

            holder.categoryName.setText(cashRecord.getCategory().getCatagoryName());
            holder.paymentType.setText(cashRecord.getPaymentType());
            holder.note.setText(cashRecord.getNotes());
            holder.amount.setText(cashRecord.getAmount());
            holder.date.setText(cashRecord.getDate());
            holder.paymentType.setText(cashRecord.getPaymentType());
        }
    }

    @Override
    public int getItemCount() {

        if (recordList != null) {
            return recordList.size();
        }
        return 0;
    }

    public void addCashRecord(CashRecord cashRecord) {
        recordList.add(cashRecord);
        notifyDataSetChanged();
    }

    public class CashRecordViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.category_image)
        ImageView categoryImage;

        @BindView(R.id.category_tv)
        TextView categoryName;

        @BindView(R.id.payment_type_tv)
        TextView paymentType;

        @BindView(R.id.note_tv)
        TextView note;

        @BindView(R.id.amount_tv)
        TextView amount;

        @BindView(R.id.date_tv)
        TextView date;

        public CashRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
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
