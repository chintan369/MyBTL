package com.agraeta.user.btl;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_ProgressDialog extends ProgressDialog {
	Context context;
	Animation myRotation;
	String comment;
	ImageView la;
	TextView img1;

	public Custom_ProgressDialog(Context context, String comment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.comment = comment.isEmpty() ? "Loading..." : comment;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressdialog);
		getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

		la = (ImageView) findViewById(R.id.img);
		img1=(TextView) findViewById(R.id.img1);

		img1.setText(comment);

		// la.setBackgroundResource(R.drawable.loading_spinner_icon);

		myRotation = AnimationUtils.loadAnimation(context, R.anim.rotate);

	}

	@Override
	public void show() {
		super.show();
		myRotation.setRepeatCount(Animation.INFINITE);
		la.startAnimation(myRotation);

	}

	@Override
	public void dismiss() {
		super.dismiss();
		//myRotation.cancel();
	}
}
