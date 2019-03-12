package app.jimmy.vocalsms;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

/**
 * @author Jimmy
 * Created on 12/3/19.
 */
public class DiffutilsCallBacks extends DiffUtil.Callback {
    List<SMSDataModel> oldList;
    List<SMSDataModel> newList;
    public DiffutilsCallBacks(List<SMSDataModel> oldList,List<SMSDataModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

}
