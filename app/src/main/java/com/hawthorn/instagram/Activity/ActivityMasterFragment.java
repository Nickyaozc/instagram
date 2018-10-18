package com.hawthorn.instagram.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawthorn.instagram.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivityMasterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityMasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityMasterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int NUM_PAGES = 2;

    //widgets
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private FragmentActivity myContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ActivityMasterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityMasterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityMasterFragment newInstance(String param1, String param2) {
        ActivityMasterFragment fragment = new ActivityMasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.getActivity().setContentView(R.layout.fragment_activity_master);
//
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_master, container, false);

        mPager = view.findViewById(R.id.act_pager);
        System.out.println(mPager);
        mPagerAdapter = new ActivityMasterFragment.ScreenSlidePagerAdapter((myContext.getSupportFragmentManager()));
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.act_tabs);
        tabLayout.setupWithViewPager(mPager);

        tabLayout.getTabAt(0).setText("Following");
        tabLayout.getTabAt(1).setText("You");


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FollowingActivityFragment();
            }else {
                return new YouActivityFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }





}
