package com.orobator.stackoverflow.databinding;

import android.view.View;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {
  @BindingAdapter({ "visible" })
  public static void setVisibility(View view, boolean visible) {
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
  }
}
