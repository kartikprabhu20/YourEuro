package com.artexceptionals.youreuro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.artexceptionals.youreuro.CustomClickListener;
import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Constants;
import com.maltaisn.icondialog.IconHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CashRecordAdapter extends RecyclerView.Adapter<CashRecordAdapter.CashRecordViewHolder> implements Filterable {

    private List<CashRecord> recordList = new ArrayList<>();
    private List<CashRecord> searchRecordList = new ArrayList<>();
    private Context context;
    private CashRecordListListener cashRecordListListener;
    CustomClickListener customClickListener;


    public CashRecordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CashRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_cash_record,viewGroup,false);

        return new CashRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashRecordViewHolder holder, int index) {

        if (holder != null){
            CashRecord cashRecord =  searchRecordList.get(index);

            holder.categoryName.setText(cashRecord.getCategory().getCatagoryName());
            if(searchRecordList.get(index).getCategory().isDefault) {
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(searchRecordList.get(index).getCategory().getImageID(), "drawable", context.getPackageName())));
            }else{
                IconHelper iconHelper = IconHelper.getInstance(context);
                iconHelper.addLoadCallback(new IconHelper.LoadCallback() {
                    @Override
                    public void onDataLoaded() {
                        holder.categoryImage.setImageDrawable(iconHelper.getIcon(Integer.parseInt(searchRecordList.get(index).getCategory().getImageID())).getDrawable(context));
                    }
                });
            }
            holder.paymentType.setText(cashRecord.getPaymentType());
            holder.note.setText(cashRecord.getNotes());
            holder.date.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date(cashRecord.getTimeStamp())));
            holder.paymentType.setText(cashRecord.getPaymentType());
            holder.currencySymbol.setText(CurrencyHelper.getSymbol(cashRecord.getCurrency()));
            holder.amount.setText(String.format("%.2f",cashRecord.getAmount()));
            holder.amount.setTextColor(Constants.CashRecordType.EXPENSE.equalsIgnoreCase(cashRecord.getCashRecordType()) ? context.getResources().getColor(R.color.red): context.getResources().getColor(R.color.green));
            holder.currencySymbol.setTextColor(Constants.CashRecordType.EXPENSE.equalsIgnoreCase(cashRecord.getCashRecordType()) ? context.getResources().getColor(R.color.red): context.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {

        if (searchRecordList != null) {
            return searchRecordList.size();
        }
        return 0;
    }

    public void addCashRecord(CashRecord cashRecord) {
        recordList.add(0,cashRecord);
        searchRecordList.add(0,cashRecord);

        Collections.sort(searchRecordList, Collections.reverseOrder());

        notifyDataSetChanged();
        cashRecordListListener.checkRecordList();
    }

    //For deleting a cashRecord from adapter view
    public void deleteCashRecord(CashRecord cashRecord){
        recordList.remove(cashRecord);
        searchRecordList.remove(cashRecord);
        notifyDataSetChanged();
        cashRecordListListener.checkRecordList();
    }

    public void addCashRecords(List cashRecordList) {
        recordList.addAll(cashRecordList);
        searchRecordList.addAll(cashRecordList);
        notifyDataSetChanged();
        cashRecordListListener.checkRecordList();

    }

    public void removeCashRecords(List cashRecordList) {
        recordList.removeAll(cashRecordList);
        searchRecordList.removeAll(cashRecordList);
        notifyDataSetChanged();
        cashRecordListListener.checkRecordList();
    }

    public void removeAllCashRecords() {
        recordList.clear();
        searchRecordList.clear();
        notifyDataSetChanged();
        cashRecordListListener.checkRecordList();

    }

    public List<CashRecord> getCashRecords() {
        return searchRecordList;
    }

    public class CashRecordViewHolder extends  RecyclerView.ViewHolder {
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

        @BindView(R.id.currency_tv)
        TextView currencySymbol;

        public CashRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customClickListener.onClick(itemView,getAdapterPosition());
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return customFilter;
    }

    private Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CashRecord> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(recordList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CashRecord cashRecord : recordList) {
                    if (cashRecord.getPaymentType().toLowerCase().contains(filterPattern)
                            || cashRecord.getCategory().getCatagoryName().toLowerCase().contains(filterPattern)
                            || cashRecord.getNotes().toLowerCase().contains(filterPattern)) {
                        filteredList.add(cashRecord);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchRecordList.clear();
            searchRecordList.addAll((List) results.values);
            notifyDataSetChanged();
            cashRecordListListener.checkRecordList();
        }
    };

    public void attachCashRecordListListener(CashRecordListListener cashRecordListListener){
        this.cashRecordListListener = cashRecordListListener;
    }
    public interface CashRecordListListener {
        void checkRecordList();
    }

    public void setClickListener(CustomClickListener clickListener) {
        customClickListener = clickListener;
    }
}
