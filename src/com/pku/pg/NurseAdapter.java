package com.pku.pg;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class NurseAdapter extends BaseAdapter{
    
    // ������ݵ�list
    private ArrayList<HashMap<String, String>> list;
    // ������
    private Context context;
    // �������벼��
    private LayoutInflater inflater = null;
    // ������
    public NurseAdapter(ArrayList<HashMap<String, String>> list, Context context){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
                                                                                              
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // ���ViewHolder����
            holder = new ViewHolder();
            // ���벼�ֲ���ֵ��convertview
            convertView = inflater.inflate(R.layout.list_nurses, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_title_nurse);
            holder.tvTel = (TextView) convertView.findViewById(R.id.item_info_nurse);
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_checkbox_nurse);
            // Ϊview���ñ�ǩ
            convertView.setTag(holder);
        } else {
            // ȡ��holder
            holder = (ViewHolder) convertView.getTag();
        }
        // ����list��TextView����ʾ
        holder.tvName.setText(list.get(position).get("ItemTitle").toString());
        holder.tvTel.setText(list.get(position).get("ItemText").toString());
        // ����flag������checkbox��ѡ��״��
        holder.cb.setChecked(list.get(position).get("ItemCheckbox").equals("true"));
        return convertView;
    }
                                                                                              
    final class ViewHolder{
        TextView tvName;
        TextView tvTel;
        CheckBox cb;
    }
}
