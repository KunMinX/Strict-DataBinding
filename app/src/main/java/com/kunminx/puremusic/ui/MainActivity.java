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

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.domain.message.PageMessenger;
import com.kunminx.puremusic.ui.base.BaseActivity;

/**
 * Create by KunMinX at 19/10/16
 */

public class MainActivity extends BaseActivity {

  private MainViewModel mState;
  private PageMessenger mMessenger;

  @Override
  protected void initViewModel() {
    mState = getActivityScopeViewModel(MainViewModel.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.activity_main, BR.vm, mState)
            .addBindingParam(BR.click, new ClickProxy());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mMessenger.getMomentResult().observe(this, moment -> {
      Toast.makeText(this, moment.getContent(), Toast.LENGTH_SHORT).show();
    });
  }

  public class ClickProxy {

    public void toSecondActivity() {
      startActivity(new Intent(MainActivity.this, EditorActivity.class));
    }
  }

  public static class MainViewModel extends ViewModel {
  }
}
