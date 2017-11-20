package org.drulabs.picblast.activities.albums;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.drulabs.picblast.R;
import org.drulabs.picblast.activities.imgur.AlbumDetails;
import org.drulabs.picblast.application.AppClass;
import org.drulabs.picblast.data.models.PixyAlbum;
import org.drulabs.picblast.di.DaggerViewComponent;
import org.drulabs.picblast.di.ViewModule;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kaushald on 15/11/17.
 */

public class AlbumList extends AppCompatActivity implements AlbumsContract.View, AlbumsAdapter
        .AlbumClickListener, SearchView.OnQueryTextListener {

    // UI Elements
    private RecyclerView rvAlbumList;
    private ProgressBar albumListLoader;
    private Toolbar toolbar;

    private AlbumsAdapter albumsAdapter;

    @Inject
    AlbumsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_layout);
        toolbar = findViewById(R.id.toolbar_album_list);
        setSupportActionBar(toolbar);
        initializeUI();

        DaggerViewComponent.builder()
                .appComponent(((AppClass) getApplicationContext()).getAppComponent())
                .viewModule(new ViewModule(this))
                .build().inject(this);
    }

    private void initializeUI() {
        albumListLoader = findViewById(R.id.album_list_loader);
        rvAlbumList = findViewById(R.id.rv_albums);
        rvAlbumList.setLayoutManager(new LinearLayoutManager(AlbumList.this));
        albumsAdapter = new AlbumsAdapter(this);
        rvAlbumList.setAdapter(albumsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album_list, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mPresenter.filterAlbums("");
                        return true;
                    }
                });
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.fetchAlbumList("imgur", "me", 1, 20, null);
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
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(AlbumsContract.Presenter presenter) {

    }

    @Override
    public void onAlbumsFetched(List<PixyAlbum> albums) {
        albumsAdapter.append(albums);
    }

    @Override
    public void onAlbumUpdated(PixyAlbum album) {
        albumsAdapter.append(album);
    }

    @Override
    public void showLoading(String message) {
        rvAlbumList.setVisibility(View.GONE);
        albumListLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rvAlbumList.setVisibility(View.VISIBLE);
        albumListLoader.setVisibility(View.GONE);
    }

    @Override
    public void navigateToAlbumDetails(Bundle albumInfo) {
        Intent albumDetailsIntent = new Intent(this, AlbumDetails.class);
        albumDetailsIntent.putExtras(albumInfo);
        startActivity(albumDetailsIntent);
    }

    @Override
    public void onAlbumClicked(PixyAlbum pixyAlbum) {
        mPresenter.onAlbumClicked(pixyAlbum);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            albumsAdapter.reset();
        }
        mPresenter.filterAlbums(newText);
        return false;
    }

    @Override
    public void clearList() {
        albumsAdapter.reset();
    }
}
