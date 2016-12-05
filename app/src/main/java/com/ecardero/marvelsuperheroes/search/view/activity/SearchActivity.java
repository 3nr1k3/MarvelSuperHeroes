package com.ecardero.marvelsuperheroes.search.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ecardero.marvelapi.MarvelApi;
import com.ecardero.marvelapi.api.objects.Character;
import com.ecardero.marvelapi.api.objects.Image;
import com.ecardero.marvelapi.api.objects.ref.DataWrapper;
import com.ecardero.marvelapi.api.params.name.character.ListCharacterParamName;
import com.ecardero.marvelapi.api.services.CharactersService;
import com.ecardero.marvelapi.api.util.ImageUtil;
import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.search.adapter.SearchAdapter;
import com.ecardero.marvelsuperheroes.search.model.Hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements TextWatcher{
    private final int TRIGGER_SEARCH = 1;
    private final long SEARCH_TRIGGER_DELAY = 500;

    private static final String PUBLIC_KEY = "yourPublicApiKey";
    private static final String PRIVATE_KEY = "yourPrivateApiKey";

    private static final int MIN_SEARCH_LENGTH = 3;
    private static final String CHARACTER_LIMIT = "100";

    private final MarvelApi mMarvelApi = new MarvelApi.Builder()
            .withApiKeys(PUBLIC_KEY,PRIVATE_KEY)
            .build();

    private final CharactersService mCharactersService = mMarvelApi.getService(CharactersService.class);


    RecyclerView mRecyclerView;
    SearchAdapter mAdapter;

    EditText mEdtSearchBox;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        if(msg.what == TRIGGER_SEARCH){
            if(mEdtSearchBox.getText().length() >= MIN_SEARCH_LENGTH){
                performCharactersCall();
            }
        }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEdtSearchBox = (EditText) findViewById(R.id.edt_search_search_hero);
        mEdtSearchBox.addTextChangedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_search_hero_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false)
        );

        mAdapter = new SearchAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        handler.removeMessages(TRIGGER_SEARCH);
        handler.sendEmptyMessageDelayed(TRIGGER_SEARCH,SEARCH_TRIGGER_DELAY);
    }

    public void updateHeroesList(ArrayList<Hero> heroes){
        if(heroes.size() > 0) {
            mRecyclerView.removeAllViews();
            mAdapter.notifyItemRangeChanged(0, heroes.size() - 1);
            mAdapter.updateData(heroes);
        }
    }

    private void performCharactersCall(){
        Map<String,String> args = new HashMap<>();
        args.put(ListCharacterParamName.NAME_STARTS_WITH.toString(), mEdtSearchBox.getText().toString());
        args.put(ListCharacterParamName.LIMIT.toString(),CHARACTER_LIMIT);
        Call<DataWrapper<Character>> call = mCharactersService.listCharacter(args);

        call.enqueue(new Callback<DataWrapper<Character>>() {
            @Override
            public void onResponse(Call<DataWrapper<Character>> call, Response<DataWrapper<Character>> response) {
                ArrayList<Hero> heroes = new ArrayList<>();
                if(response.isSuccessful()){
                    for(Character character : response.body().getData().getResults()){
                        if(character.getId() == 0)
                            continue;

                        Image avatar = character.getThumbnail();
                        Hero h = new Hero(String.valueOf(character.getId()), character.getName(), character.getDescription(), ImageUtil.getImageUrl(avatar, ImageUtil.ImageAspectRatio.STANDARD_MEDIUM));
                        heroes.add(h);
                    }
                }
                updateHeroesList(heroes);
            }

            @Override
            public void onFailure(Call<DataWrapper<Character>> call, Throwable t) {

            }
        });
    }
}
