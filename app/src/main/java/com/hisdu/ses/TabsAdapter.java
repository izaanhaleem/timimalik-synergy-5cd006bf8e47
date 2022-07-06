//package com.hisdu.ses;
//
//
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//
//
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//import com.hisdu.ses.fragment.ChecklistFragment;
//import com.hisdu.ses.fragment.ClusterFragment;
//import com.hisdu.ses.fragment.MasterRecordFragment;
//
//public class TabsAdapter extends FragmentStatePagerAdapter {
//
//    int mNumOfTabs;
//
//    public TabsAdapter(FragmentManager fm, int NoofTabs)
//
//    {
//        super(fm);
//        this.mNumOfTabs = NoofTabs;
//    }
//
//    @Override
//    public int getCount() { return mNumOfTabs; }
//
//    @Override
//    public Fragment getItem(int position){
//        switch (position)
//
//        {
//            case 0:
//                MasterRecordFragment master = new MasterRecordFragment();
//                return master;
//            case 1:
//                ChecklistFragment checklist = new ChecklistFragment();
//                return checklist;
//            case 2:
//                ClusterFragment cluster     = new ClusterFragment();
//                return cluster;
//            default:
//                return null;
//        }
//    }
//}