package com.mruelas.heroes.ui.heroList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mruelas.heroes.R;
import com.mruelas.heroes.data.model.Hero;
import com.mruelas.heroes.ui.heroDetail.HeroDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeroListAdapter extends RecyclerView.Adapter<HeroListAdapter.HeroViewHolder> {
    private final ArrayList<Hero> mHeroesList;
    private LayoutInflater mInflater;
    private Context mContext;
    public HeroListAdapter(Context context, ArrayList<Hero> mHeroesList) {
        mInflater = LayoutInflater.from(context);
        this.mHeroesList = mHeroesList;
        mContext = context;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.herolist_item,parent,false);
        return new HeroViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        String mCurrent = mHeroesList.get(position).getName();
        holder.heroName.setText(mCurrent);
        Picasso.get().load(mHeroesList.get(position).getImage().getUrl()).fit().centerCrop().into(holder.heroPicture);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetail = new Intent(mContext,HeroDetailActivity.class);
                goToDetail.putExtra("id",mHeroesList.get(position).getId());
                mContext.startActivity(goToDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeroesList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder{

        public final TextView heroName;
        public final ImageView heroPicture;
        final HeroListAdapter mAdapter;

        public HeroViewHolder(@NonNull View itemView,HeroListAdapter adapter) {
            super(itemView);
            heroName = itemView.findViewById(R.id.txtHeroName);
            heroPicture =  itemView.findViewById(R.id.ivPicture);
            this.mAdapter = adapter;
        }
    }
}
