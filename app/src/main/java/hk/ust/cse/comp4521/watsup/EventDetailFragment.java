package hk.ust.cse.comp4521.watsup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hk.ust.cse.comp4521.watsup.models.Event;

import static hk.ust.cse.comp4521.watsup.models.Activities.CALLING_ACTIVITY;
import static hk.ust.cse.comp4521.watsup.models.Activities.EVENT_DETAILS;

/**
 * A fragment representing a single Event detail screen.
 * This fragment is either contained in a {@link EventListActivity}
 * in two-pane mode (on tablets) or a {@link EventDetailActivity}
 * on handsets.
 */
public class EventDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Event mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = EventListActivity.eventsToBeShown.get(getArguments().getInt(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_detail, container, false);

        if (mItem != null) {
            Button locationButton = (Button) rootView.findViewById(R.id.locationButton);

            Typeface pacifico = Typeface.createFromAsset(getContext().getAssets(), "fonts/pacifico.ttf");
            ((TextView) rootView.findViewById(R.id.eventName)).setTypeface(pacifico);
            ((TextView) rootView.findViewById(R.id.eventDescription)).setTypeface(pacifico);
            ((TextView) rootView.findViewById(R.id.eventDate)).setTypeface(pacifico);
            ((TextView) rootView.findViewById(R.id.eventType)).setTypeface(pacifico);
            locationButton.setTypeface(pacifico);

            ((TextView) rootView.findViewById(R.id.eventNameContent)).setText(mItem.getName());
            ((TextView) rootView.findViewById(R.id.eventDateContent)).setText(mItem.getDate() + " - " + mItem.getTime());
            ((TextView) rootView.findViewById(R.id.eventTypeContent)).setText(mItem.getType());
            ((TextView) rootView.findViewById(R.id.eventDescriptionContent)).setText(mItem.getDescription());
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), MapActivity.class);
                    i.putExtra(CALLING_ACTIVITY, EVENT_DETAILS);
                    i.putExtra("coordinates",mItem.getCoordinates());
                    startActivity(i);
                }
            });

        }

        return rootView;
    }
}
