package com.daimajia.slider.library.Transformers;

/**
 * Created by daimajia on 14-5-29.
 */
import android.view.View;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

public class AccordionTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        System.out.println("position>>>"+ position);

        ViewHelper.setPivotY(view,position < 0 ? 0 : view.getWidth());
        ViewHelper.setScaleY(view,position < 0 ? 1f + position : 1f - position);
    }

}