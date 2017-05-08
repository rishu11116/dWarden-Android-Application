package com.project.rishabhsingh.dWarden;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class DonorViewAdapter extends RecyclerView.Adapter<DonorViewAdapter.ViewHolder> {

    private Context context;
    private List<DonorData> donorList;

    public DonorViewAdapter(Context context, List<DonorData> donorList) {

        this.context= context;
        this.donorList=donorList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_donor_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DonorViewAdapter.ViewHolder holder, int position) {

        holder.textDonorName.setText(donorList.get(position).getDonorName());
        holder.textDonorBranch.setText(donorList.get(position).getDonorBranch());
        holder.textDonorYear.setText(donorList.get(position).getDonorYear());
        holder.bindHolder(donorList.get(position));
    }


    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textDonorName;
        private TextView textDonorBranch;
        private TextView textDonorYear;
        private ImageButton imageCallButton;
        private ImageButton imageMailButton;
        private DonorData donorData;

        public ViewHolder(final View itemView) {
            super(itemView);

            textDonorName=(TextView)itemView.findViewById(R.id.textDonorName);
            textDonorBranch=(TextView)itemView.findViewById(R.id.textDonorBranch);
            textDonorYear=(TextView)itemView.findViewById(R.id.textDonorYear);
            imageCallButton=(ImageButton)itemView.findViewById(R.id.imageCallButton);
            imageCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,donorData.getDonorContact(),Toast.LENGTH_SHORT).show();
                }
            });
            imageMailButton=(ImageButton)itemView.findViewById(R.id.imageMailButton);
            imageMailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,donorData.getDonorEmail(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindHolder(DonorData donorData) {
            this.donorData=donorData;
        }
    }
}
