package com.ecardero.marvelsuperheroes.details.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.details.model.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Enrique Cardero on 03/12/2016.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Event> mEvents = new ArrayList<>();

    public EventAdapter(Context context, ArrayList<Event> events){
        mContext = context;
        mEvents = events;
    }

    public void updateEventList(ArrayList<Event> events){
        mEvents.clear();
        mEvents = events;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgEvent;
        private TextView mTxtEventTitle;
        private TextView mTxtEventDescription;

        public ViewHolder(View itemView){
            super(itemView);

            mImgEvent = (ImageView) itemView.findViewById(R.id.imv_item_comic_image);
            mTxtEventTitle = (TextView) itemView.findViewById(R.id.txt_item_comic_title);
            mTxtEventDescription = (TextView) itemView.findViewById(R.id.txt_item_comic_description);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);

        ViewHolder viewRowHolder = new ViewHolder(viewRow);

        return viewRowHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(mEvents.get(position).getImage()).resize(200,200).centerCrop().into(holder.mImgEvent);

        holder.mTxtEventTitle.setText(mEvents.get(position).getName());
        holder.mTxtEventDescription.setText(mEvents.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
