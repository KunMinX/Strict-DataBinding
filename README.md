![](https://tva1.sinaimg.cn/large/008i3skNly1gre5lfycabj312e0awjsf.jpg)

# Strict-DataBinding

## DataBinding 严格模式

正如[《Jetpack MVVM 精讲》](https://juejin.im/post/6844903976240939021)中提到的，我们在表现层使用 DataBinding 而不是 “直接调用视图实例” 或 ViewBinding，是为了 <mark>**通过 “可观察数据” 间接通知视图刷新，来规避可能存在的 视图实例为 null 的安全隐患**</mark>。也即 DataBinding 的本质是 **解决视图调用的一致性问题**。

然而与基于函数式编程思想的 "声明式 UI" 框架 Jetpack Compose 不同的是，DataBinding 并非是通过纯函数的方式来隔绝手写代码对 “视图实例” 的接触，而是透过 “自动化代码生成” 的方式来为视图实例做 ”判空处理“，

> 而这也就带来了一个问题 —— 你可以在代码中透过 Binding 实例来调用视图实例 —— 如此等于舍本逐末、前功尽弃。

因而基于对 “**解决视图调用一致性问题**” 的独家理解，“DataBinding 严格模式” 应运而生，通过它，可使视图调用一致性问题 **被彻底 (100%) 解决**、安全性与 Jetpack Compose 持平。

很高兴有不少小伙伴告诉我，他们已将这种开发模式用在实际项目的开发中。

![](https://i.loli.net/2021/06/10/oaOjR8BwhDVTugC.jpg)


## Maven 依赖

- 以下 implementation 的命名，我们已从 `archi` 改为 `arch`，请注意修改，
- 鉴于 Jcenter 的关闭，我们已将仓库迁移至 Maven Central，请自行在根目录 build.gradle 添加 `mavenCentral()`。

```groovy
//核心：DataBinding 严格模式基础框架
implementation 'com.kunminx.arch:strict-databinding:3.7.0-beta1'

//可选：常用 BindingAdapter 接口整理
implementation 'com.kunminx.arch:binding-adapter:3.4.0-beta1'
//可选：常用 RecyclerView Binding 接口整理
implementation 'com.kunminx.arch:binding-recyclerview:3.9.0-beta1'
```

> **温馨提示：**
>
> 在使用 “DataBinding 严格模式” 后，对于 “属性动画” 等 “对视图实例强依赖” 的场景，可借助 “Motion 动画” 等新式框架代替（确定 Motion 动画的学习成本不足属性动画的 20%，且效果好、收益高，具体视频教程可见我们在[《MotionChallenge》](https://github.com/Jetpack-Missionary/MotionChallenge)的分享）。
>
> 如对 Jetpack Compose 基于函数式编程思想 “解决视图调用一致性问题” 的理论基础感兴趣，可详见[《是 “一通百通” 的 声明式 UI 扫盲干货》](https://xiaozhuanlan.com/topic/2356748910)的铺垫，此处不做累述。


## 谁在使用

感谢小伙伴们对 “开源库使用情况” 匿名调查问卷的参与，截至 2021年4月25日，我们了解到

包括 “腾讯音乐、BMW、TCL” 在内的诸多知名厂商的软件，都参考过我们开源的 [Jetpack MVVM Scaffold](https://github.com/KunMinX/Jetpack-MVVM-Scaffold) 架构模式，以及正在使用我们维护的 [UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData) 等框架。

目前我们已将具体的统计数据更新到 相关的开源库 ReadMe 中，问卷调查我们也继续保持开放，不定期将小伙伴们登记的公司和产品更新到表格，以便吸引到更多的小伙伴 参与到对这些架构组件的 使用、反馈，集众人之所长，让架构组件得以不断演化和升级。

https://wj.qq.com/s2/8362688/124a/

| 集团 / 公司                                         | 产品           |
| --------------------------------------------------- | -------------- |
| 左医科技                                            | 诊室听译机器人 |
| 福建树叶网络科技有限公司 <br> 福建天奖网络科技有限公司 | 天奖谱林       |


## 版权声明

本文以 [CC 署名-非商业性使用-禁止演绎 4.0 国际协议](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.zh) 发行。

Copyright © 2019-present KunMinX

![](https://images.xiaozhuanlan.com/photo/2020/8fc6f51263babeb544bb4a7dae6cde59.jpg)

ReadMe 中提到的 “对 DataBinding 用于规避视图实例 null 安全问题的理解” 等多处 **对特定现象及其本质的概括，均属于本人独立原创的成果**，本人对此享有最终解释权。

任何个人或组织在转载全文，或引用本文中上述提到的 描述、举例、图例或本质概括 时，**须注明原作者和出处**。未经授权不得用于洗稿、广告包装等商业用途。

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