package com.wipro.shishir.demoapp;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wipro.shishir.demoapp.model.MainData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.TourViewHolder> {

    private MainData tourPlacesList;
    private Picasso picasso;

    TourListAdapter(MainData mainData) {
        this.tourPlacesList = mainData;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tour, parent, false);
        ButterKnife.bind(this, view);

        // This is here because we do not want to use context from views or activity instead we can
        // get it from the view
        if (picasso == null) {
            picasso = new Picasso.Builder(parent.getContext()).build();
        }
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TourViewHolder holder, int position) {
        final Context context = holder.tourLayout.getContext();
        final int adapterPosition = holder.getAdapterPosition();

        final String title = tourPlacesList.getRows().get(adapterPosition).getTitle();
        final String description = tourPlacesList.getRows().get(adapterPosition).getDescription();

        Object imageUrl = tourPlacesList.getRows().get(adapterPosition).getImageHref();
        imageUrl = imageUrl != null ? imageUrl : "";

        holder.titleTextView.setText(!TextUtils.isEmpty(title) ? title
                : context.getString(R.string.no_title));
        holder.text_description.setText(!TextUtils.isEmpty(description) ? description
                : context.getString(R.string.no_description));
        holder.tourImage.setImageDrawable(context
                .getResources().getDrawable(R.drawable.ic_launcher_background, context.getTheme()));

        // This is the default visibilty to avoid flickering of images or may be miss positioning
        // of them while loading or scrolling.
        holder.tourImage.setVisibility(View.GONE);

        final Object finalImageUrl = imageUrl;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty((String) finalImageUrl)) {
                    holder.tourImage.setVisibility(View.GONE);
                } else {
                    loadImage((String) finalImageUrl, holder.tourImage);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Object rows = tourPlacesList.getRows();
        return rows == null ? 0 : ((List) rows).size();
    }

    // Picasso load images only when the view is visible, So it can be like
    // lazy loading or loading when required
    private void loadImage(String finalImageUrl, final ImageView imageView) {
        picasso.load(finalImageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        imageView.setVisibility(View.GONE);
                    }
                });
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_tour_item)
        CardView tourLayout;

        @BindView(R.id.text_title)
        TextView titleTextView;

        @BindView(R.id.text_description)
        TextView text_description;

        @BindView(R.id.image_tour)
        ImageView tourImage;

        TourViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}