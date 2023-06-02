package gr.orestislef.roomhandling.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gr.orestislef.roomhandling.R;
import gr.orestislef.roomhandling.room.tables.MyObj;

public class MyObjRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MyObj> myObjs;

    public MyObjRVAdapter(ArrayList<MyObj> myObjs) {
        this.myObjs = myObjs;
    }



    @SuppressLint("NotifyDataSetChanged")
    public void add(ArrayList<MyObj> myObjs) {
        if (this.myObjs != null)
            this.myObjs.clear();
        if (this.myObjs == null)
            this.myObjs = new ArrayList<>();
        this.myObjs.addAll(myObjs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.single_my_obj, parent, false);
        return new MyObjViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyObjViewHolder mHolder = (MyObjViewHolder) holder;
        MyObj currentItem = myObjs.get(position);

        mHolder.idTV.setText(String.valueOf(currentItem.getId()));
        mHolder.nameTV.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        if (myObjs == null)
            return 0;
        return myObjs.size();
    }

    public static class MyObjViewHolder extends RecyclerView.ViewHolder {
        TextView idTV, nameTV;

        public MyObjViewHolder(@NonNull View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.idTV);
            nameTV = itemView.findViewById(R.id.nameTV);

        }
    }
}
