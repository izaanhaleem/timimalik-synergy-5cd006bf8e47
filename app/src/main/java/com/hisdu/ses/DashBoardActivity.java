package com.hisdu.ses;

import androidx.fragment.app.FragmentActivity;
import android.app.FragmentManager;
import android.os.Bundle;
import com.hisdu.ses.fragment.ModulesListFragment;

public class DashBoardActivity extends FragmentActivity {

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new ModulesListFragment()).addToBackStack(null).commit();
    }
}