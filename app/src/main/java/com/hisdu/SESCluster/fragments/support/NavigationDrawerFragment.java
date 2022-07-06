package com.hisdu.SESCluster.fragments.support;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.SESCluster.objects.MenuObject;
import com.hisdu.ses.R;
import com.hisdu.SESCluster.adapters.NavigationDrawerAdapter;
import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.database.LocalDatabaseManager;
import com.hisdu.SESCluster.interfaces.NavigationCallbacks;
import com.hisdu.SESCluster.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Naeem
 */
public class NavigationDrawerFragment extends Fragment implements NavigationCallbacks, View.OnClickListener {
    public static String Tag = NavigationDrawerFragment.class.getSimpleName();

    protected LocalDatabaseManager mDbManager;

    private NavigationCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    public DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private int mCurrentSelectedPosition;
    private ImageView mImgClose;
    boolean isArabic = false;
    boolean isLogin = false;
    int selectedPosition = 0;
    String userInfo;
    //UserMainObject userMainObject;
    int notificationCount = 0;
    public List<MenuObject> menuItems;
    ImageView ivBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        isArabic = isArabic();
        Globals.MENU_SELECTED_INDEX = 0;
//        mDbManager = LocalDatabaseManager.getInstance(getActivity());
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
//        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);
        mImgClose = (ImageView) view.findViewById(R.id.img_cross);
        mImgClose.setOnClickListener(this);
        menuItems = getDrawerItems();
        adapter = new NavigationDrawerAdapter(menuItems, getActivity());
        adapter.setNavigationCallbacks(this);
//        ivBack.setOnClickListener(this);
        mDrawerList.setAdapter(adapter);
        //selectRow(mCurrentSelectedPosition);
        return view;
    }

    public void updateSelectedIndex() {
        mDrawerList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public void initDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.color_red));

    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mCallbacks = null;
//    }

    public List<MenuObject> getDrawerItems() {
        List<MenuObject> items = new ArrayList<MenuObject>();
        return items;
    }

//    public List<MenuObject> getDrawerItemsWithCancle() {
//        List<MenuObject> items = new ArrayList<MenuObject>();
//        items.add(new MenuObject(getString(R.string.cancel_campaign), R.drawable.menu_home_inactive, R.drawable.menu_home_active, -1));
//        items.add(new MenuObject(getString(R.string.switch_account), R.drawable.menu_home_inactive, R.drawable.menu_home_active, -1));
//        items.add(new MenuObject(getString(R.string.logout), R.drawable.menu_home_inactive, R.drawable.menu_home_active, -1));
//        return items;
//    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemSelected(int position) {

        selectRow(position);


    }

    @Override
    public void onLanguageSelected(boolean isChecked) {
        if (mCallbacks != null)
            mCallbacks.onLanguageSelected(isChecked);

    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    void selectRow(int position) {
        if (position < 13) {

            mCurrentSelectedPosition = position;
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }

            selectedPosition = position;
            if (mCallbacks != null) {
                mCallbacks.onItemSelected(position);
            }
            ((NavigationDrawerAdapter) mDrawerList.getAdapter()).setSelectedRow(position);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == ivBack) {
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
        }
//        if (view == mImgClose) {
//            if (mDrawerLayout != null) {
//                mDrawerLayout.closeDrawer(mFragmentContainerView);
//            }
//        }
    }

    protected boolean isArabic() {
        return !Utils.getLanguage(getActivity()).equalsIgnoreCase("en");
    }

    public void upadteAdapter() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

//    public void updateNavigationAdapterToCreateCampaign() {
//        menuItems.clear();
//        menuItems = getDrawerItemsWithCancle();
//        adapter = new NavigationDrawerAdapter(menuItems, getActivity());
//        adapter.setNavigationCallbacks(this);
//        mDrawerList.setAdapter(adapter);
//    }

    public void updateNavigationAdapter() {
        menuItems.clear();
        menuItems = getDrawerItems();
        adapter = new NavigationDrawerAdapter(menuItems, getActivity());
        adapter.setNavigationCallbacks(this);
        mDrawerList.setAdapter(adapter);
    }

    /*public void updateNotificationCount(int count) {
        menuItems.remove(8);
        notificationCount = count;
        menuItems.add(8, new MenuObject(getString(R.string.menu_notifications), R.drawable.menu_notifications_inactive, R.drawable.menu_notifications_active, notificationCount));
        adapter.updateList(menuItems);


//        adapter = new NavigationDrawerAdapter(menuItems, getActivity());
//        adapter.setNavigationCallbacks(this);
//        mDrawerList.setAdapter(adapter);
    }

    private void getUnreadNotificationsCount(ArrayList<NotificationItemObject> notifications) {
        for (int i = 0; i < notifications.size(); i++) {
            if (!Utils.isNotificationRead(notifications.get(i), getActivity()))
                notificationCount += 1;
        }
    }*/


}
