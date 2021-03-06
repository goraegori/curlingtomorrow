package com.gor2.curlingtomorrow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gor2.curlingtomorrow.Curlingtomorrow;
import com.gor2.curlingtomorrow.MainActivity;
import com.gor2.curlingtomorrow.R;
import com.gor2.curlingtomorrow.adapter.ResultAdapter;
import com.gor2.curlingtomorrow.dataclass.Result;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ResultsFrag extends Fragment {
    ResultAdapter resultAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_results, container, false);

        ArrayList<Result> results = ((Curlingtomorrow)container.getContext().getApplicationContext()).GetResultsList();

        RecyclerView recyclerView = root.findViewById(R.id.recycler_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        resultAdapter = new ResultAdapter(results,getActivity());
        recyclerView.setAdapter(resultAdapter);

        RefreshInsertedFirst();
        return root;
    }

    public void RefreshInsertedFirst(){
        if(resultAdapter != null)
            resultAdapter.notifyItemInserted(0);
    }

    public void RefreshRemovedIndex(int index){
        if(resultAdapter != null)
            resultAdapter.notifyItemRemoved(index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == MainActivity.REQUESTCODE){
            if(resultCode==RESULT_OK){
                RefreshInsertedFirst();
            }
            else if(resultCode==Curlingtomorrow.RESULT_DELETED){
                int position = data.getIntExtra("deleteposition",0);
                Log.e("test","notify removed: "+position);
                RefreshRemovedIndex(position);
            }
        }
    }
}

