package com.hisdu.SESCluster.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hisdu.SESCluster.constants.Globals;
import com.hisdu.SESCluster.interfaces.NavigationCallbacks;
import com.hisdu.SESCluster.objects.MenuObject;
import com.hisdu.ses.R;

import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<MenuObject> mData;
    //private int mSelectedPosition;
    private int mTouchedPosition = -1;
    private NavigationCallbacks mNavigationCallbacks;
    private final int MENU_ITEM = 0, LANGUAGE_MENU_ITEM = 1;
    Boolean isClicked = true;
    int lastPosition = -1;

    public NavigationDrawerAdapter(List<MenuObject> data, Context context) {
        mData = data;
        this.mContext = context;
    }

    public void updateList(List<MenuObject> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setNavigationCallbacks(NavigationCallbacks navigationCallbacks) {
        mNavigationCallbacks = navigationCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == MENU_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_row_item, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_row_item, viewGroup, false);
            return new LanguageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {


        if (viewHolder instanceof ViewHolder) {
            ViewHolder menuViewHolder = (ViewHolder) viewHolder;

            menuViewHolder.textView.setText(mData.get(i).getTitle());
//            if (i == 1) {
//                if (mData.get(i).getNotificationCount() >= 0) {
//                    menuViewHolder.notificationTxt.setVisibility(View.VISIBLE);
//                    menuViewHolder.notificationTxt.setText(String.valueOf(mData.get(i).getNotificationCount()));
//                }
//            } else {
//                menuViewHolder.notificationTxt.setVisibility(View.GONE);
//            }

        /* handle gestures and click events */
            handleRowEvents(viewHolder.itemView, i);

        /* Highlight selected row */
            if (Globals.MENU_SELECTED_INDEX == i) {

//                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                menuViewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
                menuViewHolder.ivHighlighted.setVisibility(View.INVISIBLE);
//                menuViewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.color_dark_grey_text));
            }

           if (i == mData.size()-1)
                menuViewHolder.menuBar.setVisibility(View.GONE);
        } else if (viewHolder instanceof LanguageViewHolder) {

            LanguageViewHolder languageViewHolder = (LanguageViewHolder) viewHolder;


            languageViewHolder.textView.setText(mData.get(i).getTitle());




        }
    }



    private void handleRowEvents(View itemView, final int i) {
        /*itemView.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {

                                            switch (event.getAction()) {
                                                case MotionEvent.ACTION_DOWN:
                                                    touchPosition(i);
                                                    return false;
                                                case MotionEvent.ACTION_UP:
                                                case MotionEvent.ACTION_CANCEL:
                                                    touchPosition(-1);
                                                    return false;
                                                case MotionEvent.ACTION_MOVE:
                                                    return false;
                                            }
                                            return true;
                                        }
                                    }
        );*/
        itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (mNavigationCallbacks != null)
                                                mNavigationCallbacks.onItemSelected(i);
                                        }
                                    }
        );
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 13)
            return LANGUAGE_MENU_ITEM;
        else
            return MENU_ITEM;
    }
    public void setSelectedRow(int position) {
        if (position < 13) {
            int lastPosition = Globals.MENU_SELECTED_INDEX;
            Globals.MENU_SELECTED_INDEX = position;

        /* Required to update the color selection */
            notifyItemChanged(lastPosition);
            notifyItemChanged(position);
        }
    }

    @Override
    public int getItemCount() {
        /* Do null check to avoid NullPointerExceptions */
        return mData != null ? mData.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, notificationTxt;
        ImageView imageView, ivHighlighted;
        protected View menuBar, menuBarTop;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.menu_title);
            notificationTxt = (TextView) itemView.findViewById(R.id.menu_notification);
            imageView = (ImageView) itemView.findViewById(R.id.menu_image);
            ivHighlighted = (ImageView) itemView.findViewById(R.id.ivMenuHighLight);
            this.menuBar = itemView.findViewById(R.id.menu_bar);


        }
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        Button languageButton;


        public LanguageViewHolder(View itemView) {
            super(itemView);

        }

    }
}