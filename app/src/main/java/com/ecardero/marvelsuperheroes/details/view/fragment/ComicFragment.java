package com.ecardero.marvelsuperheroes.details.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.details.adapter.ComicAdapter;
import com.ecardero.marvelsuperheroes.details.model.Comic;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ComicAdapter mAdapter;
    private ArrayList<Comic> mComics = new ArrayList<>();

    public ComicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_comic, container, false);

        mRecyclerView = (RecyclerView) viewFragment.findViewById(R.id.rcv_fragment_comic_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(),
                LinearLayoutManager.VERTICAL,
                false)
        );

        mAdapter = new ComicAdapter(this.getContext(), mComics);
        mRecyclerView.setAdapter(mAdapter);

        return viewFragment;
    }

    public void updateData(ArrayList<Comic> comics) {
        if(comics.size() > 0) {
            if (mRecyclerView != null)
                mRecyclerView.removeAllViews();

            mAdapter.notifyItemRangeChanged(0, mComics.size() - 1);
            mAdapter.updateComicList(comics);
        }
    }

    public boolean isRecyclerAvailable(){
        return mRecyclerView != null;
    }
}
