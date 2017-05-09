package com.project.rishabhsingh.dWarden.DonorSearchingSystem;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rishabhsingh.dWarden.DonorSearchingSystem.DonorData;
import com.project.rishabhsingh.dWarden.R;

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

                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Permission !!!");
                    alertDialog.setIcon(R.drawable.phone_permission);
                    alertDialog.setMessage("Do you want to call " +donorData.getDonorName()+ " ?");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+ donorData.getDonorContact()));
                            if (ActivityCompat.checkSelfPermission(context,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            context.startActivity(callIntent);
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });
            imageMailButton=(ImageButton)itemView.findViewById(R.id.imageMailButton);
            imageMailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Permission !!!");
                    alertDialog.setIcon(R.drawable.email);
                    alertDialog.setMessage("Do you want to mail "+donorData.getDonorName()+" ?");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mailIntent = new Intent(Intent.ACTION_SEND);
                            mailIntent.setData(Uri.parse("mailto:"));
                            mailIntent.setType("message/rfc822");
                            mailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{donorData.getDonorEmail()});
                            if (mailIntent.resolveActivity(context.getPackageManager()) != null) {
                                context.startActivity(Intent.createChooser(mailIntent, "Send mail via..."));
                            }
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });
        }

        public void bindHolder(DonorData donorData) {
            this.donorData=donorData;
        }
    }
}
