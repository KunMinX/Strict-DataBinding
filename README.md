![](https://images.xiaozhuanlan.com/photo/2021/e3b68f1ec4b7be9762e87827cbc284f1.png)

&nbsp;

## DataBinding 严格模式

如[《Jetpack MVVM 精讲》](https://juejin.im/post/6844903976240939021)所述，DataBinding 本质是 **解决 View 实例 Null 安全一致性问题**。

然与函数式编程思想 "声明式 UI" 框架 Jetpack Compose 区别在于，DataBinding 并非通过 “纯函数” 方式隔绝手写代码对 “View 实例” 接触，而是 “自动化代码生成” 方式为 View 实例做 ”判空处理“，

> 这也就带来一问题 —— 开发者可在代码中通过 mBinding 实例调用 View 实例 —— 如此等于舍本逐末、前功尽弃。

故基于该理解，“DataBinding 严格模式” 应运而生，通过屏蔽 mBinding 使 View 实例 Null 安全一致性问题 **被彻底解决**，安全性与 Jetpack Compose 持平。

```java
public class EditorFragment extends BaseFragment {
  private EditorStates mState;

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_editor, BR.vm, mState)
            .addBindingParam(BR.click, new ClickProxy());
  }
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    ...
    mMomentRequest.getResult().observe(getViewLifecycleOwner(), result -> {
      mState.showEditorBar.set(result.editBarVisible);
    });
  }

  public static class EditorStates extends ViewModel {
    public final State<Boolean> showEditorBar = new State<>(true);
  }
}
```

&nbsp;


## Maven 依赖

鉴于 Jcenter 关闭，我们已将仓库迁移至 Maven Central，请自行于根目录 build.gradle 添加 `mavenCentral()`。

```groovy
//核心：DataBinding 严格模式基础框架
implementation 'com.kunminx.arch:strict-databinding:5.6.0'

//可选：去防抖 State
implementation 'com.kunminx.arch:binding-state:5.6.0'
//可选：常用 RecyclerView Binding 接口整理
implementation 'com.kunminx.arch:binding-recyclerview:5.6.0'
```

&nbsp;

> **温馨提示：**
>
> 使用 “DataBinding 严格模式” 后，“属性动画” 等 “对 View 实例强依赖” 场景，可借助 “Motion 动画” 等新式框架代替（确定 Motion 动画学习成本不足属性动画 20%，且效果好、收益高，具体视频教程可见我们在[《MotionChallenge》](https://github.com/Jetpack-Missionary/MotionChallenge)分享）。
>
> 如对 Jetpack Compose 基于函数式编程思想 “解决 View 实例 Null 安全一致性问题” 理论基础感兴趣，详见[《一通百通 “声明式 UI” 扫盲干货》](https://xiaozhuanlan.com/topic/2356748910)，此处不做累述。

&nbsp;

## 谁在使用

根据小伙伴们私下反馈和调查问卷，我们了解到

包括 “腾讯音乐、BMW、TCL” 在内的诸多知名厂商的软件，都参考过我们开源的 [Jetpack MVVM Scaffold](https://github.com/KunMinX/Jetpack-MVVM-Scaffold) 架构模式，及正在使用我们维护的 UnPeek-LiveData 等框架。

“问卷调查” 我们长期保持对外开放，如有意可自行登记，以便吸引更多小伙伴 参与到对这些架构组件的使用和反馈，集众人之所长，让架构组件得以不断演化和升级。

https://wj.qq.com/s2/8362688/124a/

| 集团 / 公司                                            | 产品           |
| ------------------------------------------------------ | -------------- |
| 左医科技                                               | 诊室听译机器人 |

&nbsp;

## 版权声明

Copyright © 2019-present KunMinX

ReadMe 中提到的对 “DataBinding 用于规避 View 实例 Null 安全一致性问题” 的理解 等多处 **对特定现象及其本质的概括，均属于本人独立原创的成果**，本人对此享有最终解释权。

任何个人或组织在转载全文，或引用本文中上述提到的 描述、举例、图例或本质概括 时，**须注明原作者和出处**。未经授权不得用于洗稿、广告包装等商业用途。

&nbsp;

```
Copyright 2019-present KunMinX

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```