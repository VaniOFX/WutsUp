package hk.ust.cse.comp4521.watsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hk.ust.cse.comp4521.watsup.dummy.Observer;
import hk.ust.cse.comp4521.watsup.models.Event;

import java.util.List;

/**
 * An activity representing a list of Events. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EventDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EventListActivity extends AppCompatActivity implements Observer {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private static final String TAG = "EventListActivity";
    public static String CALLING_ACTIVITY = "calling_activity";
    public static String OPTIONS_ACTIVITY = "options_activity";
    public static String PROFILE_ACTIVITY = "profile_activity";

    private boolean mTwoPane;
    public static List<Event> eventsToBeShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        DataBaseCommunicator.addObserver(this);

        String callingActivity = getIntent().getStringExtra(EventListActivity.CALLING_ACTIVITY);

        if(callingActivity.equals(OPTIONS_ACTIVITY)){
            eventsToBeShown = DataBaseCommunicator.eventsList;
        }
        else if(callingActivity.equals(PROFILE_ACTIVITY)){
            eventsToBeShown = DataBaseCommunicator.enrolledEvents;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("SOMETAG", getTitle().toString());
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventListActivity.this, MapActivity.class);
                i.putExtra(MapActivity.CALLING_ACTIVITY,MapActivity.EXPLORE_ACTIVITY);
                startActivity(i);
            }
        });

        if (findViewById(R.id.event_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    @Override
    protected void onStart(){
        super.onStart();
        DataBaseCommunicator.setUpDataBase();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        DataBaseCommunicator.removeObserver(this);
    }

    @Override
    public void update() {
        View recyclerView = findViewById(R.id.event_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, eventsToBeShown, mTwoPane));
    }


    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final EventListActivity mParentActivity;
        private final List<Event> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event item = (Event) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(EventDetailFragment.ARG_ITEM_ID, eventsToBeShown.indexOf(item));
                    EventDetailFragment fragment = new EventDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.event_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra(EventDetailFragment.ARG_ITEM_ID, eventsToBeShown.indexOf(item));

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(EventListActivity parent,
                                      List<Event> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if(mValues.get(position) != null) {
                holder.mIdView.setText(mValues.get(position).getName());
                holder.mContentView.setText(mValues.get(position).getType());

                holder.itemView.setTag(mValues.get(position));
                holder.itemView.setOnClickListener(mOnClickListener);
            }
        }

        @Override
        public int getItemCount() {

            return mValues == null ? 0 : mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
