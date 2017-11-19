package org.drulabs.picblast.activities.imgur;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.drulabs.picblast.R;
import org.drulabs.picblast.data.models.PixyPic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaushald on 19/11/17.
 */

public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.PicVH> {

    private PicsListener mListener;
    private List<PixyPic> mPics;

    public PicsAdapter(PicsListener picsListener) {
        this.mListener = picsListener;
        mPics = new ArrayList<>();
    }

    @Override
    public PicVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_image_big, parent, false);
        return new PicVH(itemView);
    }

    @Override
    public void onBindViewHolder(PicVH holder, int position) {
        holder.bind(position);
    }

    public void append(List<PixyPic> pics){
        this.mPics.removeAll(pics);
        this.mPics.addAll(pics);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPics.size();
    }

    class PicVH extends RecyclerView.ViewHolder {

        ImageView imgPixyPic;
        TextView tvTitle, tvDesc;

        PicVH(View itemView) {
            super(itemView);
            imgPixyPic = itemView.findViewById(R.id.img_bg_main);
            tvTitle = itemView.findViewById(R.id.tv_uploader_info);
            tvDesc = itemView.findViewById(R.id.tv_album_info);
        }

        void bind(int position) {
            PixyPic pic = mPics.get(position);
            tvTitle.setText(pic.getName());
            tvDesc.setText(pic.getDescription());
            Picasso.with(itemView.getContext())
                    .load(pic.getProviderUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgPixyPic);
            imgPixyPic.setBackgroundResource(R.drawable.top_bottom_grey_gradient);
            itemView.setOnClickListener(v -> mListener.onPicClicked(pic));
        }

    }

    interface PicsListener {
        void onPicClicked(PixyPic pic);
    }
}
