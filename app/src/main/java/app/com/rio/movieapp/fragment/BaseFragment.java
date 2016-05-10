package app.com.rio.movieapp.fragment;

import android.support.v4.app.Fragment;

import app.com.rio.movieapp.activity.BaseActivity;

/**
 * Created by rio on 10/05/16.
 */
public class BaseFragment extends Fragment {

    public boolean isOnline() { return ((BaseActivity) getContext()).isOnline(getActivity());}
}
