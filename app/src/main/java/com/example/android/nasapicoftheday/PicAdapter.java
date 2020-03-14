package com.example.android.nasapicoftheday;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.nasapicoftheday.utils.PicList;

import java.util.ArrayList;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicItemViewHolder> {

    //    private ArrayList<String> mForecastData;
    private ArrayList<PicList> mPicData;
    private PicList mPic;
    private OnPicItemClickListener mOnPicItemClickListener;

    public PicAdapter (OnPicItemClickListener onPicItemClickListener) {
        mOnPicItemClickListener = onPicItemClickListener;
    }

    public void updatePicData(PicList picData) {
        mPic = picData;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPicData != null) {
            return mPicData.size();
        } else {
            return 0;
        }
    }

    @Override
    public PicItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pic_list_item, parent, false);
        return new PicItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PicItemViewHolder holder, int position) {
        holder.bind(mPicData.get(position));
    }

    public interface OnPicItemClickListener {
        void onPicItemClick(PicList forecast);
    }

    class PicItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mPicTextView;

        public PicItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mPicTextView = itemView.findViewById(R.id.tv_title_text);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String detailedForecast = mDetailedForecastData.get(getAdapterPosition());
//                    mOnForecastItemClickListener.onForecastItemClick(mForecastData.get(getAdapterPosition()));
//                }
//            });
        }

        public void bind(PicList pic) {
            mPicTextView.setText(pic.title);
        }
    }
}
