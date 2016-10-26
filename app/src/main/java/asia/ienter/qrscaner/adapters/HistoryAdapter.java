package asia.ienter.qrscaner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import asia.ienter.qrscaner.R;
import asia.ienter.qrscaner.models.HistoryClass;
import asia.ienter.qrscaner.utils.Utils;

/**
 * Created by phamquangmanh on 10/25/16.
 */
public class HistoryAdapter extends BaseAdapter {

    ArrayList<HistoryClass> historyList;
    Context context;
    LayoutInflater inflater;
    public HistoryAdapter(Context context,ArrayList<HistoryClass> historyList){
        this.historyList = historyList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return (historyList==null ? 0 : historyList.size());
    }

    @Override
    public HistoryClass getItem(int position) {
        return  (historyList==null ? null : historyList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_history, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(historyList.get(position).getContent());
        holder.DateTime.setText(Utils.timeAgoDetail(context,historyList.get(position).getDate()));

        return convertView;
    }

    private class ViewHolder {
        public TextView tvContent,DateTime;

        public ViewHolder(View convertView) {
            tvContent = (TextView) convertView.findViewById(R.id.contentQR);
            DateTime = (TextView) convertView.findViewById(R.id.dateTimeQR);
        }
    }
}
