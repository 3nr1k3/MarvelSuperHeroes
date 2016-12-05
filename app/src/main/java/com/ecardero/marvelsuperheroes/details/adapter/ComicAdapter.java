package com.ecardero.marvelsuperheroes.details.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.details.model.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Enrique Cardero on 03/12/2016.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Comic> mComics = new ArrayList<>();

    public void updateComicList(ArrayList<Comic> comics) {
        mComics.clear();
        mComics = comics;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgComic;
        private TextView mTxtComicTitle;
        private TextView mTxtComicDescription;

        public ViewHolder(View itemView){
            super(itemView);

            mImgComic = (ImageView) itemView.findViewById(R.id.imv_item_comic_image);
            mTxtComicTitle = (TextView) itemView.findViewById(R.id.txt_item_comic_title);
            mTxtComicDescription = (TextView) itemView.findViewById(R.id.txt_item_comic_description);
        }
    }

    public ComicAdapter(Context context, ArrayList<Comic> comics){
        mContext = context;
        mComics = comics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);

        ViewHolder viewRowHolder = new ViewHolder(viewRow);

        return viewRowHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(mComics.get(position).getImage()).resize(200,200).centerCrop().into(holder.mImgComic);

        holder.mTxtComicTitle.setText(mComics.get(position).getTitle());
        holder.mTxtComicDescription.setText(mComics.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mComics.size();
    }
}
