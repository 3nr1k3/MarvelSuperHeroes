package com.ecardero.marvelsuperheroes.details.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecardero.marvelapi.MarvelApi;
import com.ecardero.marvelapi.api.exceptions.AuthorizationException;
import com.ecardero.marvelapi.api.exceptions.EntityNotFoundException;
import com.ecardero.marvelapi.api.exceptions.QueryException;
import com.ecardero.marvelapi.api.exceptions.RateLimitException;
import com.ecardero.marvelapi.api.objects.Character;
import com.ecardero.marvelapi.api.objects.Comic;
import com.ecardero.marvelapi.api.objects.Event;
import com.ecardero.marvelapi.api.objects.Image;
import com.ecardero.marvelapi.api.objects.Url;
import com.ecardero.marvelapi.api.objects.ref.DataWrapper;
import com.ecardero.marvelapi.api.params.name.character.GetCharacterEventsParamName;
import com.ecardero.marvelapi.api.services.CharactersService;
import com.ecardero.marvelapi.api.util.ImageUtil;
import com.ecardero.marvelsuperheroes.R;
import com.ecardero.marvelsuperheroes.details.adapter.TabAdapter;
import com.ecardero.marvelsuperheroes.details.model.HeroDetails;
import com.ecardero.marvelsuperheroes.details.view.fragment.ComicFragment;
import com.ecardero.marvelsuperheroes.details.view.fragment.EventFragment;
import com.ecardero.marvelsuperheroes.search.model.Hero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeroDetailsActivity extends AppCompatActivity{
    private static final String TAG = HeroDetailsActivity.class.getSimpleName();

    private static final String PUBLIC_KEY = "yourPublicApiKey";
    private static final String PRIVATE_KEY = "yourPrivateApiKey";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ComicFragment mComicFragment;
    private EventFragment mEventFragment;

    private ImageView mImgHeroAvatar;
    private TextView mTxtHeroName;
    private TextView mTxtHeroDescription;

    private LinearLayout mLnlButtonHolder;

    private String mTabComicsText;
    private String mTabEventsText;

    private Intent mIntent;
    private Integer mHeroId;

    HeroDetails.Builder mHeroDetails = new HeroDetails.Builder();

    private final MarvelApi marvelApi = new MarvelApi.Builder()
            .withApiKeys(PUBLIC_KEY,PRIVATE_KEY)
            .build();

    private final CharactersService mCharactersService = marvelApi.getService(CharactersService.class);

    TabAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);

        mImgHeroAvatar = (ImageView) findViewById(R.id.imv_detail_hero_avatar);
        mTxtHeroName = (TextView) findViewById(R.id.txt_detail_hero_name);
        mTxtHeroDescription = (TextView) findViewById(R.id.txt_detail_hero_description);

        mComicFragment = new ComicFragment();
        mEventFragment = new EventFragment();



        mIntent = getIntent();
        mHeroId = Integer.valueOf(mIntent.getStringExtra("hero_id"));

        Log.d(TAG, String.valueOf(mHeroId));

        mLnlButtonHolder = (LinearLayout) findViewById(R.id.lnl_btn_holder);

        performCharacterCall();
        performCharacterComicsCall();
        performCharacterEventsCall();
    }

    private void setupViewPager(ViewPager viewPager){
        mTabAdapter = new TabAdapter(getSupportFragmentManager());

        mTabAdapter.addFragment(mComicFragment, mTabComicsText);
        mTabAdapter.addFragment(mEventFragment, mTabEventsText);

        viewPager.setAdapter(mTabAdapter);
    }

    public void updateInfo(HeroDetails details){
        if(mImgHeroAvatar != null)
            Picasso.with(this).load(details.getImage()).resize(200,200).centerCrop().into(mImgHeroAvatar);

        mTxtHeroName.setText(details.getName());
        mTxtHeroDescription.setText(details.getDescription());

        for (final Url url : details.getUrls()){
            Button bt = new Button(this);
            bt.setText(url.getType());
            bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url.getUrl()));
                    startActivity(intent);
                }
            });
            mLnlButtonHolder.addView(bt);
        }

        mTabComicsText = String.format("(%d) %s", details.getTotalComics(), getResources().getString(R.string.tab_detail_comic));
        mTabEventsText = String.format("(%d) %s", details.getTotalEvents(), getResources().getString(R.string.tab_detail_event));

        setupTabLayout();
    }

    private void setupTabLayout(){


        mViewPager = (ViewPager) findViewById(R.id.vwp_details);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tbl_details);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void performCharacterCall(){
        Call<DataWrapper<Character>> characterCall = null;
        try {
            characterCall = mCharactersService.getCharacter(mHeroId);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        characterCall.enqueue( new Callback<DataWrapper<Character>>(){
            @Override
            public void onResponse(Call<DataWrapper<Character>> call, Response<DataWrapper<Character>> response) {
                if(response.isSuccessful()){
                    Character character = response.body().getData().getResults().get(0);

                    Image avatar = character.getThumbnail();
                    Hero hero = new Hero(String.valueOf(character.getId()), character.getName(), character.getDescription(), ImageUtil.getImageUrl(avatar, ImageUtil.ImageAspectRatio.STANDARD_MEDIUM));

                    HeroDetails heroDetails = mHeroDetails.withHero(hero)
                            .withUrls(character.getUrls())
                            .setTotalComics(character.getComics().getAvailable())
                            .setTotalEvents(character.getEvents().getAvailable())
                            .build();

                    updateInfo(heroDetails);
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<Character>> call, Throwable t) {

            }
        });
    }

    private void performCharacterComicsCall(){

        Map<String,String> options = new HashMap<>();
        options.put(GetCharacterEventsParamName.LIMIT.toString(),"100");
        Call<DataWrapper<Comic>> comicCall = null;
        try {
            comicCall = mCharactersService.getCharacterComics(mHeroId, options);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        comicCall.enqueue(new Callback<DataWrapper<Comic>>() {
            @Override
            public void onResponse(Call<DataWrapper<Comic>> call, Response<DataWrapper<Comic>> response) {
                if(response.isSuccessful()){
                    ArrayList<com.ecardero.marvelsuperheroes.details.model.Comic> myComics = new ArrayList<>();
                    for (Comic comic : response.body().getData().getResults()){
                        Image thumbnail = comic.getThumbnail();
                        myComics.add(new com.ecardero.marvelsuperheroes.details.model.Comic(
                                ImageUtil.getImageUrl(thumbnail, ImageUtil.ImageAspectRatio.STANDARD_MEDIUM),
                                comic.getTitle(),
                                comic.getDescription())
                        );
                    }
                    if(myComics != null && mComicFragment.isRecyclerAvailable())
                        mComicFragment.updateData(myComics);
                }
            }

            @Override
            public void onFailure(Call<DataWrapper<Comic>> call, Throwable t) {
                Log.e(TAG, t.getMessage(),t);
            }
        });
    }

    private void performCharacterEventsCall(){

        Call<DataWrapper<Event>> eventCall = null;
        try {
            eventCall = mCharactersService.getCharacterEvents(mHeroId);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        eventCall.enqueue(new Callback<DataWrapper<Event>>() {
            @Override
            public void onResponse(Call<DataWrapper<Event>> call, Response<DataWrapper<Event>> response) {
                if (response.isSuccessful()) {
                    ArrayList<com.ecardero.marvelsuperheroes.details.model.Event> myEvents = new ArrayList<>();
                    for(Event event : response.body().getData().getResults()){
                        Image thumbnail = event.getThumbnail();
                        myEvents.add(new com.ecardero.marvelsuperheroes.details.model.Event(
                                event.getTitle(),
                                event.getDescription(),
                                ImageUtil.getImageUrl(thumbnail, ImageUtil.ImageAspectRatio.STANDARD_MEDIUM))
                        );
                    }
                    if(myEvents != null && mEventFragment.isRecyclerAvailable())
                        mEventFragment.updateData(myEvents);
                }
                findViewById(R.id.lnl_details_loader).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DataWrapper<Event>> call, Throwable t) {

            }
        });
    }
}
