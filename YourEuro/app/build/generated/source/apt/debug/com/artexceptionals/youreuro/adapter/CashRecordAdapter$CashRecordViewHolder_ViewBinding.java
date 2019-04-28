// Generated code from Butter Knife. Do not modify!
package com.artexceptionals.youreuro.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.artexceptionals.youreuro.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CashRecordAdapter$CashRecordViewHolder_ViewBinding implements Unbinder {
  private CashRecordAdapter.CashRecordViewHolder target;

  @UiThread
  public CashRecordAdapter$CashRecordViewHolder_ViewBinding(
      CashRecordAdapter.CashRecordViewHolder target, View source) {
    this.target = target;

    target.categoryImage = Utils.findRequiredViewAsType(source, R.id.category_image, "field 'categoryImage'", ImageView.class);
    target.categoryName = Utils.findRequiredViewAsType(source, R.id.category_tv, "field 'categoryName'", TextView.class);
    target.paymentType = Utils.findRequiredViewAsType(source, R.id.payment_type_tv, "field 'paymentType'", TextView.class);
    target.note = Utils.findRequiredViewAsType(source, R.id.note_tv, "field 'note'", TextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.amount_tv, "field 'amount'", TextView.class);
    target.date = Utils.findRequiredViewAsType(source, R.id.date_tv, "field 'date'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CashRecordAdapter.CashRecordViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.categoryImage = null;
    target.categoryName = null;
    target.paymentType = null;
    target.note = null;
    target.amount = null;
    target.date = null;
  }
}
