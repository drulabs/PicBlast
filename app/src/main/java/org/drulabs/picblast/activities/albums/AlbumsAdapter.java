package org.drulabs.picblast.activities.albums;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.drulabs.picblast.R;
import org.drulabs.picblast.data.models.PixyAlbum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kaushald on 15/11/17.
 */

class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumVH> {

    private List<PixyAlbum> albums;
    private AlbumClickListener mListener;

    AlbumsAdapter(AlbumClickListener listener) {
        this.albums = new ArrayList<>();
        this.mListener = listener;
    }

    @Override
    public AlbumVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_image_big, parent, false);
        AlbumVH albumVH = new AlbumVH(itemView);
        return albumVH;
    }

    @Override
    public void onBindViewHolder(AlbumVH holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void append(List<PixyAlbum> albums) {
        this.albums.removeAll(albums);
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    public void append(PixyAlbum album) {
        this.albums.remove(album);
        this.albums.add(album);
        notifyDataSetChanged();
    }

    public void reset() {
        this.albums.clear();
        notifyDataSetChanged();
    }

    class AlbumVH extends RecyclerView.ViewHolder {

        ImageView imgCover;
        TextView tvAlbumInfo;
        TextView tvUploaderInfo;

        AlbumVH(View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_bg_main);
            tvAlbumInfo = itemView.findViewById(R.id.tv_album_info);
            tvUploaderInfo = itemView.findViewById(R.id.tv_uploader_info);
        }

        void bind(int position) {
            PixyAlbum pixyAlbum = albums.get(position);
            Picasso.with(itemView.getContext())
                    .load(pixyAlbum.getCover())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgCover);
            @SuppressLint("DefaultLocale")
            String albumInfo = String.format("%s (%d) - %s", pixyAlbum.getTitle(), pixyAlbum
                    .getImagesCount(), pixyAlbum.getProvider());
            tvAlbumInfo.setText(albumInfo);

            tvUploaderInfo.setText(String.format(Locale.US, "Views: %d", pixyAlbum.getViews()));

            itemView.setOnClickListener(v -> mListener.onAlbumClicked(pixyAlbum));
        }
    }

    interface AlbumClickListener {
        void onAlbumClicked(PixyAlbum pixyAlbum);
    }
}
