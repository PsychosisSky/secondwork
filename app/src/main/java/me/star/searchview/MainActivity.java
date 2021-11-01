package me.star.searchview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SearchView searchView;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Adapter();
        datas = generateDatas();
        adapter.setDatas(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.setDatas(datas);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setDatas(search(newText));
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    private List<String> search(String query) {
        List<String> filterDatas = new ArrayList<>();
        for (String source : datas) {
            if (source.contains(query)) {
                filterDatas.add(source);
            }
        }
        return filterDatas;
    }

    private List<String> generateDatas() {
        List<String> list = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            list.add("这是第" + (index + 1)+"行");
        }
        return list;
    }

    class Adapter extends RecyclerView.Adapter {
        private List<String> datas = new ArrayList<>();

        public void setDatas(List<String> datas) {
            this.datas = datas;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
