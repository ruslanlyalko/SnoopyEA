package com.example.android.snoopyea.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.android.snoopyea.R;
import com.example.android.snoopyea.models.EventsAdapter;

public class EventsActivity extends AppCompatActivity{//} implements EventsAdapter.ItemClickCallBack {

    private RecyclerView recyclerView;
    private EventsAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        setContentView(R.layout.activity_events);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*
        eventAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventAdapter);


        ItemTouchHelper touchHelper = new ItemTouchHelper(createHelperCallback());
        touchHelper.attachToRecyclerView(recyclerView);
*/

    }
/*
    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };


        return simpleCallback;
    }

    private void deleteItem(int adapterPosition) {

        Toast.makeText(this, "Deleted "+ adapterPosition, Toast.LENGTH_SHORT).show();
        eventAdapter.notifyItemRemoved(adapterPosition);
    }

    private void moveItem(int adapterPosition, int adapterPosition1) {

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
