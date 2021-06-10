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

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.ui.adapter.MomentAdapter;
import com.kunminx.puremusic.ui.base.BaseFragment;
import com.kunminx.puremusic.ui.event.SharedViewModel;
import com.kunminx.puremusic.ui.state.ListViewModel;

/**
 * Create by KunMinX at 2020/5/30
 */
public class ListFragment extends BaseFragment {

    private ListViewModel mState;
    private SharedViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(ListViewModel.class);
        mEvent = getActivityScopeViewModel(SharedViewModel.class);
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

        mState.getListMutableLiveData().observe(getViewLifecycleOwner(), moments -> {
            mState.list.setValue(moments);
        });

        mEvent.moment.observe(this, moment -> {
            mState.list.getValue().add(0, moment);
            mState.list.setValue(mState.list.getValue());
        });

        mState.requestList();
    }

    public class ClickProxy {
        public void fabClick() {
            nav().navigate(R.id.action_listFragment_to_editorFragment);
        }
    }
}
