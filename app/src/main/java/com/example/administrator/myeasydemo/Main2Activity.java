package com.example.administrator.myeasydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.recyclerView_main2)
    RecyclerView recyclerViewMain2;

    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        for (int i = 0; i < 50; i++) {
            data.add("*****" + i + "*****");
        }
        recyclerViewMain2.setLayoutManager(new GridLayoutManager(this, 2));
        MyAdapter adapter = new MyAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(Main2Activity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(Main2Activity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewMain2.setAdapter(adapter);
    }



    class MyAdapter extends RecyclerView.Adapter<MyViewHoulder> {
        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHoulder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHoulder(LayoutInflater.from(Main2Activity.this).inflate(R.layout.layout_item_main2, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHoulder holder, final int position) {
            holder.txt.setText(data.get(position));
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickListener.onItemLongClick(v, position);
                        return true;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }

    interface OnItemClickListener {

        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);

    }

    class MyViewHoulder extends RecyclerView.ViewHolder {
        TextView txt;

        public MyViewHoulder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt_item_main2);
        }
    }
}
