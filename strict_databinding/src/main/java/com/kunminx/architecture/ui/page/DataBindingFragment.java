/*
 *
 *  * Copyright 2018-present KunMinX
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.kunminx.architecture.ui.page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.kunminx.strictdatabinding.R;


/**
 * Create by KunMinX at 19/7/11
 */
public abstract class DataBindingFragment extends Fragment {

  protected AppCompatActivity mActivity;
  private ViewDataBinding mBinding;
  private TextView mTvStrictModeTip;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mActivity = (AppCompatActivity) context;
  }

  protected abstract void initViewModel();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initViewModel();
  }

  protected abstract DataBindingConfig getDataBindingConfig();

  /**
   * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
   * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
   * <p>
   * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
   *
   * @return binding
   */
  protected ViewDataBinding getBinding() {
    if (isDebug() && mBinding != null) {
      if (mTvStrictModeTip == null) {
        mTvStrictModeTip = new TextView(getContext());
        mTvStrictModeTip.setAlpha(0.5f);
        mTvStrictModeTip.setPadding(
                mTvStrictModeTip.getPaddingLeft() + 24,
                mTvStrictModeTip.getPaddingTop() + 64,
                mTvStrictModeTip.getPaddingRight() + 24,
                mTvStrictModeTip.getPaddingBottom() + 24);
        mTvStrictModeTip.setGravity(Gravity.CENTER_HORIZONTAL);
        mTvStrictModeTip.setTextSize(10);
        mTvStrictModeTip.setBackgroundColor(Color.WHITE);
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
        String tip = getString(R.string.debug_databinding_warning, getClass().getSimpleName());
        mTvStrictModeTip.setText(tip);
        ((ViewGroup) mBinding.getRoot()).addView(mTvStrictModeTip);
      }
    }
    return mBinding;
  }

  @Nullable
  @Override
  public View onCreateView(
          @NonNull LayoutInflater inflater,
          @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState
  ) {

    DataBindingConfig dataBindingConfig = getDataBindingConfig();

    //TODO tip: DataBinding 严格模式：
    // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
    // 通过这样的方式，来彻底解决 视图调用的一致性问题，
    // 如此，视图调用的安全性将和基于函数式编程思想的 Jetpack Compose 持平。

    // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

    ViewDataBinding binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
    binding.setLifecycleOwner(getViewLifecycleOwner());
    binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
    SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
    for (int i = 0, length = bindingParams.size(); i < length; i++) {
      binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
    }
    mBinding = binding;
    return binding.getRoot();
  }

  public boolean isDebug() {
    return mActivity.getApplicationContext().getApplicationInfo() != null &&
            (mActivity.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mBinding.unbind();
    mBinding = null;
  }
}
