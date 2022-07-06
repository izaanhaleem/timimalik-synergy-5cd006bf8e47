package com.hisdu.SESCluster.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.hisdu.ses.R;
import com.wang.avi.AVLoadingIndicatorView;


public class TransparentProgressDialog extends Dialog {
	Context mCtx;
	Handler h;
	private ImageView iv;
//	LoaderView loaderView;


	public TransparentProgressDialog(Context context) {
		super(context, R.style.TransparentProgressDialog);

		h = new Handler();

		setTitle(null);
		setCancelable(false);
		setOnCancelListener(null);
		mCtx = context;
		setContentView(R.layout.loading_dialog);

		AVLoadingIndicatorView av = (AVLoadingIndicatorView)findViewById(R.id.avloadingIndicatorView);
		av.setIndicator("BallGridPulseIndicator");
		if(Utils.getColorMode(getContext()).equals("colorful"))
			av.setIndicatorColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
		else
			av.setIndicatorColor(getContext().getResources().getColor(R.color.color_grey_scale));

	}



	@Override
	public void show() {
		super.show();
	}


}

