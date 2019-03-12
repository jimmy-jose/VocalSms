package app.jimmy.vocalsms;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Jimmy
 * Created on 21/2/19.
 */
public class SMSRCViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements Filterable {

    private ArrayList<SMSDataModel> feedsList = new ArrayList<>();
    private ArrayList<SMSDataModel> backupList;
    private int[] headerPosArray;
    private static final String TAG = SMSRCViewAdapter.class.getSimpleName();

    public void setPosArray(int[] posArray) {
        headerPosArray = posArray;
    }

    class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView body;
        TextView timestamp;

        SmsViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.header);
            body = v.findViewById(R.id.body);
            timestamp = v.findViewById(R.id.timestamp);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        HeaderViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.text);
        }
    }

    public SMSRCViewAdapter() {
    }


    public void updateList(ArrayList<SMSDataModel> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffutilsCallBacks(this.feedsList, newList));
        feedsList.clear();
        feedsList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        for (int i:
             headerPosArray) {
            Log.d(TAG, "onCreateViewHolder: "+i);
        }
        switch (viewType){
            case 0: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_header, parent, false);
                return new HeaderViewHolder(v);
            }
            case 1: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_sms, parent, false);
                return new SmsViewHolder(v);
            }
            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SmsViewHolder) {
            if(feedsList.get(position).getDate()!=null){
                ((SmsViewHolder)holder).header.setText(feedsList.get(position).getFrom());
                ((SmsViewHolder)holder).body.setText(feedsList.get(position).getBody());
                ((SmsViewHolder)holder).timestamp.setText((new Date(Long.valueOf(feedsList.get(position).getDate()))).toString());
            }
        }else if(holder instanceof HeaderViewHolder){
            if(feedsList.get(position).getBody()!=null) {
                ((HeaderViewHolder)holder).text.setText(feedsList.get(position).getBody());
            }
        }
    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    feedsList = backupList;
                } else {
                    ArrayList<SMSDataModel> filteredList = new ArrayList<>();
                    for (SMSDataModel row : backupList) {
                        if (row.getFrom().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    feedsList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = feedsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                feedsList = (ArrayList<SMSDataModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        if(isInPosArray(position)){
            return 0;
        }else {
            return 1;
        }
    }

    private boolean isInPosArray(int position) {
        for (int a: headerPosArray) {
            if(position==a)
                return true;
        }
        return false;
    }
}



