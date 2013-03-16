package org.bjzhou.directmsg.views;

import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.ui.ConversationActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class MyFragment extends Fragment implements OnScrollListener, OnItemClickListener, OnRefreshListener<ListView> {
	
    private PullToRefreshListView listview;
	public BaseAdapter baseAdapter;
	public boolean isFling = false;
	
	public abstract void updateView();
	public abstract String getUidOnItemClick(int position);
	
    /**
     * when activity is recycled by system, isFirstTimeStartFlag will be reset to default true,
     * when activity is recreated because a configuration change for example screen rotate, isFirstTimeStartFlag will stay false
     */
    private boolean isFirstTimeStartFlag = true;

    protected static final int FIRST_TIME_START = 0; //when activity is first time start
    protected static final int SCREEN_ROTATE = 1;    //when activity is destroyed and recreated because a configuration change, see setRetainInstance(boolean retain)
    protected static final int ACTIVITY_DESTROY_AND_CREATE = 2;  //when activity is destroyed because memory is too low, recycled by android system

    protected int getCurrentState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            isFirstTimeStartFlag = false;
            return ACTIVITY_DESTROY_AND_CREATE;
        }


        if (!isFirstTimeStartFlag) {
            return SCREEN_ROTATE;
        }

        isFirstTimeStartFlag = false;
        return FIRST_TIME_START;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View contentView = inflater.inflate(R.layout.fragment_home, container,
				false);
		listview = (PullToRefreshListView) contentView.findViewById(R.id.lv_home);
		listview.setOnScrollListener(this);
		listview.setOnItemClickListener(this);
		listview.setOnRefreshListener(this);
        return contentView;
    }
	
	public void onBackPressed() {
		getActivity().finish();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		switch(arg1) {
		case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
			updateView();
			break;
		case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
			isFling = true;
			break;
		case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			isFling = false;
			break;
		}
	}
	
	public PullToRefreshListView getListView() {
		return listview;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
		Intent intent = new Intent(getActivity(),ConversationActivity.class);
		String uid = getUidOnItemClick(postition - 1);
		intent.putExtra("uid", uid);
		startActivity(intent);
	}
	
}
