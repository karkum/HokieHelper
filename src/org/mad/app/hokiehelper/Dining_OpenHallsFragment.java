package org.mad.app.hokiehelper;

import java.util.ArrayList;

import org.mad.app.hokiehelper.Dining_DiningHall.DiningHallState;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class Dining_OpenHallsFragment extends SherlockFragment {
	private View rootView;
	private GridView gridHalls;
	private TextView txtNoHallsMessage;
	private Dining_Handler handler = new Dining_Handler();
	private ArrayList<Dining_DiningHall> hallsForState = handler.getHallsForState(DiningHallState.OPEN);
	private GridAdapter gridAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.dining_hall_grid, container, false);
		txtNoHallsMessage = (TextView) rootView.findViewById(R.id.textview_dining_halls_grid);
		txtNoHallsMessage.setVisibility(TextView.GONE);

		// set up the grid that shows the list of dining halls and its listener
		gridHalls = (GridView) rootView.findViewById(R.id.gridview_dining_halls);
		gridHalls.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent i = new Intent(getActivity(), Dining_InfoActivity.class);
				i.putExtra("selectedHall", hallsForState.get(position).getHallId());
				startActivity(i);
			}
		});
		gridAdapter = new GridAdapter(getActivity(), inflater);
		gridHalls.setAdapter(gridAdapter);
		update();
		return rootView;
	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public GridAdapter(Context c, LayoutInflater i) {
			mInflater = i;
		}

		public int getCount() {
			return hallsForState.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = mInflater.inflate(R.layout.dining_hall_grid_icon, parent, false);
			TextView text = (TextView) v.findViewById(R.id.dining_hall_grid_text);
			text.setText(hallsForState.get(position).getName());
			ImageView img = (ImageView) v.findViewById(R.id.dining_hall_grid_icon);
			img.setImageResource(hallsForState.get(position).getIconId());
			return v;
		}
	}

	public void update() {
		hallsForState.clear();
		hallsForState = handler.getHallsForState(DiningHallState.OPEN);
		if (hallsForState.isEmpty()) {
			txtNoHallsMessage.setVisibility(TextView.VISIBLE);
			gridHalls.setVisibility(GridView.GONE);
			txtNoHallsMessage.setText("It appears that no dining halls are currently open.");
		} else {
			txtNoHallsMessage.setVisibility(TextView.GONE);
			gridHalls.setVisibility(GridView.VISIBLE);
			gridAdapter.notifyDataSetChanged();
			gridHalls.invalidateViews();
		}
	}
}