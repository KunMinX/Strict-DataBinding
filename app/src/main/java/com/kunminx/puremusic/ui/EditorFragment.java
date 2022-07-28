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
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.State;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.Moment;
import com.kunminx.puremusic.domain.message.PageMessenger;
import com.kunminx.puremusic.ui.base.BaseFragment;

import java.util.UUID;

/**
 * Create by KunMinX at 2020/5/30
 */
public class EditorFragment extends BaseFragment {

  private EditorViewModel mState;
  private PageMessenger mMessenger;

  @Override
  protected void initViewModel() {
    mState = getFragmentScopeViewModel(EditorViewModel.class);
    mMessenger = getActivityScopeViewModel(PageMessenger.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_editor, BR.vm, mState)
            .addBindingParam(BR.click, new ClickProxy());
  }

  public class ClickProxy implements Toolbar.OnMenuItemClickListener {

    public void locate() {

    }

    public void back() {
      nav().navigateUp();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
      if (item.getItemId() == R.id.menu_save) {
        toggleSoftInput();
        Moment moment = new Moment(
                UUID.randomUUID().toString(),
                mState.content.get(),
                mState.location.get(),
                null,
                "KunMinX",
                "https://tva1.sinaimg.cn/large/e6c9d24ely1h4exa8m7quj20ju0juaax.jpg"
        );
        mMessenger.requestMoment(moment);
        nav().navigateUp();
      }
      return true;
    }
  }

  public static class EditorViewModel extends ViewModel {
    public final State<String> content = new State<>("");
    public final State<String> location = new State<>("台北夜市一条街");
  }
}
