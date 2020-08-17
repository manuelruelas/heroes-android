package com.mruelas.heroes.ui.heroDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mruelas.heroes.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HeroDetailActivity extends AppCompatActivity {
    HeroDetailViewModel heroDetailViewModel;
    TextView mTxtHeroName;
    TextView mTxtRealName;
    TextView mTxtPublisher;
    TextView mTxtFirstAppearance;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        initViews();
        heroDetailViewModel = new ViewModelProvider(this).get(HeroDetailViewModel.class);
        heroDetailViewModel.init();

        initObservers();
        String id = getIntent().getStringExtra("id");
        heroDetailViewModel.getHero(id);
    }
    void initViews(){
        mTxtHeroName = findViewById(R.id.txtName);
        mTxtRealName = findViewById(R.id.txtRealName);
        mTxtFirstAppearance = findViewById(R.id.txtFirstAppearance);
        mTxtPublisher = findViewById(R.id.txtPublisher);
        circleImageView = findViewById(R.id.ivCircle);
    }
    void initObservers(){
        heroDetailViewModel.getHeroInfo().observe(this,hero->{
            if(hero != null){
                Picasso.get().load(hero.getImage().getUrl()).fit().into(circleImageView);
                mTxtHeroName.setText(hero.getName());
                String realName = mTxtRealName.getText() + hero.getBiography().getRealName();
                String firstAppeareance = mTxtFirstAppearance.getText() + hero.getBiography().getFirstAppearance();
                String publisher = mTxtPublisher.getText() + hero.getBiography().getPublisher();
                mTxtRealName.setText(realName);
                mTxtFirstAppearance.setText(firstAppeareance);
                mTxtPublisher.setText(publisher);
            }


        });
    }
}
