package com.daimajia.slider.library.Transformers;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class FlipHorizontalTransformer extends BaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		ViewHelper.setPivotY(view, position < 360 ? 360 : view.getWidth());
		ViewHelper.setScaleY(view, position < 360 ? 1f + position : 1f - position);
	}

}
