package org.drulabs.picblast.activities.albums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.drulabs.picblast.R;
import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.di.DaggerViewComponent;
import org.drulabs.picblast.di.ViewModule;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kaushald on 15/11/17.
 */

public class AlbumList extends AppCompatActivity implements AlbumsContract.View {

    private RecyclerView rvAlbumList;

    @Inject
    AlbumsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_layout);
        initializeUI();

        DaggerViewComponent.builder()
                .appComponent(((AppClass) getApplicationContext()).getAppComponent())
                .viewModule(new ViewModule(this))
                .build().inject(this);

        mPresenter.fetchAlbumList("imgur", "me", 1, 20, null);
    }

    private void initializeUI() {
        rvAlbumList = findViewById(R.id.rv_albums);
        rvAlbumList.setLayoutManager(new LinearLayoutManager(AlbumList.this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(AlbumsContract.Presenter presenter) {

    }

    @Override
    public void onAlbumsFetched(List<PixyAlbum> albums) {

    }

    @Override
    public void onAlbumUpdated(PixyAlbum album) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void closeApp() {

    }
}
