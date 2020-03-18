package com.example.android.nasapicoftheday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.nasapicoftheday.utils.PicList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedPicsAdapter extends RecyclerView.Adapter<SavedPicsAdapter.SearchResultViewHolder> {
    private List<PicList> mSearchResultsList;
    private OnSearchResultClickListener mResultClickListener;

    interface OnSearchResultClickListener {
        void onSearchResultClicked(PicList repo);
    }

    public SavedPicsAdapter(OnSearchResultClickListener listener) {
        mResultClickListener = listener;
    }

    public void updatePicResults(List<PicList> searchResultsList) {
        mSearchResultsList = searchResultsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSearchResultsList != null) {
            return mSearchResultsList.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pic_list_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.bind(mSearchResultsList.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mPicTextView;
        private ImageView mImageView;
        private TextView mDate;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            mPicTextView = itemView.findViewById(R.id.tv_title_text);
            mImageView = itemView.findViewById(R.id.imageview);
            mDate = itemView.findViewById(R.id.tv_date_text);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResultClickListener.onSearchResultClicked(
                            mSearchResultsList.get(getAdapterPosition())
                    );
                }
            });
        }

        void bind(PicList pic) {
            mPicTextView.setText("Title: " + pic.title);
            mDate.setText("Date: " + pic.date);
            Picasso.get().load(pic.url).into(mImageView);
        }
    }
}
