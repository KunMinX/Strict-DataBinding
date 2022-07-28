/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kunminx.puremusic.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.State;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.Moment;
import com.kunminx.puremusic.domain.message.PageMessenger;
import com.kunminx.puremusic.domain.request.MomentRequest;
import com.kunminx.puremusic.ui.adapter.MomentAdapter;
import com.kunminx.puremusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 2020/5/30
 */
public class ListFragment extends BaseFragment {

  private ListViewModel mState;
  private PageMessenger mMessenger;
  private MomentRequest mMomentRequest;

  @Override
  protected void initViewModel() {
    mState = getFragmentScopeViewModel(ListViewModel.class);
    mMessenger = getActivityScopeViewModel(PageMessenger.class);
    mMomentRequest = getFragmentScopeViewModel(MomentRequest.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {

    return new DataBindingConfig(R.layout.fragment_list, BR.vm, mState)
            .addBindingParam(BR.click, new ClickProxy())
            .addBindingParam(BR.adapter, new MomentAdapter(mActivity.getApplicationContext()));
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mMomentRequest.getListResult().observe(getViewLifecycleOwner(), moments -> {
      mState.list.addAll(moments);
    });

    mMessenger.getMomentResult().observe(getViewLifecycleOwner(), moment -> {
      mState.list.add(0, moment);
    });

    mMomentRequest.requestList();
  }

  public class ClickProxy {
    public void fabClick() {
      nav().navigate(R.id.action_listFragment_to_editorFragment);
    }
  }

  public static class ListViewModel extends ViewModel {
    public final ObservableArrayList<Moment> list = new ObservableArrayList<>();
    public final State<Boolean> autoScrollToTopWhenInsert = new State<>(true);
  }
}
