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

import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.Moment;
import com.kunminx.puremusic.ui.base.BaseActivity;
import com.kunminx.puremusic.ui.callback.SharedViewModel;
import com.kunminx.puremusic.ui.state.EditorViewModel;

import java.util.UUID;

/**
 * Create by KunMinX at 2020/5/30
 */
public class EditorActivity extends BaseActivity {

    private EditorViewModel mEditorViewModel;
    private SharedViewModel mSharedViewModel;

    @Override
    protected void initViewModel() {
        mEditorViewModel = getActivityScopeViewModel(EditorViewModel.class);
        mSharedViewModel = getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_editor, BR.vm, mEditorViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    public class ClickProxy implements Toolbar.OnMenuItemClickListener {

        public void locate() {

        }

        public void back() {
            finish();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.menu_save) {
                toggleSoftInput();
                Moment moment = new Moment();
                moment.setUuid(UUID.randomUUID().toString());
                moment.setUserName("KunMinX");
                moment.setLocation(mEditorViewModel.location.get());
                moment.setContent(mEditorViewModel.content.get());
                mSharedViewModel.moment.setValue(moment);
                finish();
            }
            return true;
        }
    }
}
