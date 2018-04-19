package com.example.admin.note_hub;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 4/13/2018.
 */

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.View_Holder> {
    public List<String> fileNameList;
    public List<String> fileDoneList;


    public UploadListAdapter(List<String> fileNameList , List<String> fileDoneList){
        this.fileDoneList = fileDoneList;
        this.fileNameList = fileNameList;


    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       View_Holder v = new View_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single ,parent , false));
       return v;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        String fileName = fileNameList.get(position);
        holder.fileNameView.setText(fileName);
        String fileDone = fileDoneList.get(position);
        if (fileDone.equals("uploading"))
        {
            holder.fileDoneView.setImageResource(R.drawable.ic_progress);
        }
        else {
            holder.fileDoneView.setImageResource(R.drawable.ic_checked);

        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder{
        View mView;
        public TextView fileNameView;
        public ImageView fileDoneView;
        public View_Holder(View itemView){
            super(itemView);
            mView = itemView;
            fileNameView = (TextView) mView.findViewById(R.id.upload_filename);
            fileDoneView = (ImageView) mView.findViewById(R.id.upload_loading);

        }

    }
}
