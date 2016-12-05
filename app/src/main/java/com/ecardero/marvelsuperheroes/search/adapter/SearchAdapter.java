package com.ecardero.marvelsuperheroes.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.details.view.activity.HeroDetailsActivity;
import com.ecardero.marvelsuperheroes.search.model.Hero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Enrique Cardero on 02/12/2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Hero> mHeroes = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mImgAvatar;
        private TextView mTxtName;
        private TextView mTxtDescription;
        private String _Id;

        private Context activityContext;

        public ViewHolder(Context context, View itemView){
            super(itemView);

            itemView.setOnClickListener(this);

            activityContext = context;
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imv_item_hero_avatar);
            mTxtName = (TextView) itemView.findViewById(R.id.txt_item_hero_name);
            mTxtDescription = (TextView) itemView.findViewById(R.id.txt_item_hero_description);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activityContext, HeroDetailsActivity.class);
            intent.putExtra("hero_id", _Id);
            activityContext.startActivity(intent);
        }
    }

    public SearchAdapter(Context context){
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero, parent, false);

        ViewHolder viewRowHolder = new ViewHolder(mContext, viewRow);

        return viewRowHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(mHeroes.get(position).getImage()).resize(200,200).centerCrop().into(holder.mImgAvatar);

        holder.mTxtName.setText(mHeroes.get(position).getName());
        holder.mTxtDescription.setText(mHeroes.get(position).getDescription());
        holder._Id = mHeroes.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mHeroes.size();
    }

    public void updateData(ArrayList<Hero> heroes){
        mHeroes.clear();
        mHeroes = heroes;
    }
}
