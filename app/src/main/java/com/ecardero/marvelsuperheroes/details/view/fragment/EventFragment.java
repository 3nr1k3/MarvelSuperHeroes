package com.ecardero.marvelsuperheroes.details.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.details.adapter.EventAdapter;
import com.ecardero.marvelsuperheroes.details.model.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private ArrayList<Event> mEvents = new ArrayList<>();

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFragment = inflater.inflate(R.layout.fragment_event, container, false);

        mRecyclerView = (RecyclerView) viewFragment.findViewById(R.id.rcv_fragment_event_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getContext(),
                LinearLayoutManager.VERTICAL,
                false)
        );

        mAdapter = new EventAdapter(this.getContext(), mEvents);
        mRecyclerView.setAdapter(mAdapter);

        return viewFragment;
    }

    public void updateData(ArrayList<Event> events){
        mEvents = events;
        if(events.size() > 0){
            if (mRecyclerView != null)
                mRecyclerView.removeAllViews();

            mAdapter.notifyItemRangeChanged(0, events.size()-1);
            mAdapter.updateEventList(events);
        }
    }

    public boolean isRecyclerAvailable(){
        return mRecyclerView != null;
    }

}
