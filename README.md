
# Strict-DataBinding

## DataBinding 严格模式

正如[《Jetpack MVVM 精讲》](https://juejin.im/post/6844903976240939021)中提到的，我们在表现层使用 DataBinding 而不是 “直接调用视图实例” 或 ViewBinding，是为了 <mark>**通过 “可观察数据” 间接通知视图刷新，来规避可能存在的 视图实例为 null 的安全隐患**</mark>。也即 DataBinding 的本质是 **解决视图调用的一致性问题**。

然而与基于函数式编程思想的 Flutter/Jetpack Compose 不同的是，DataBinding 并非是通过纯函数的方式来隔绝手写代码对 “视图实例” 的接触，而是透过 “自动化代码生成” 的方式来为视图实例做 ”判空处理“，

而这也就带来了一个问题 —— 你可以在代码中透过 Binding 实例来调用视图实例 —— 如此等于舍本逐末、前功尽弃。

因而基于对 “**解决视图调用一致性问题**” 的独家理解，“DataBinding 严格模式” 应运而生，通过它，可使视图调用一致性问题 **被彻底 (100%) 解决**、安全性与 Jetpack Compose 持平。

很高兴有不少小伙伴告诉我，他们已将这种开发模式用在实际项目的开发中。

![](https://images.xiaozhuanlan.com/photo/2020/f1f045d61a37de4cb269937ee8d78e4e.jpg)

```groovy
//核心
implementation 'com.kunminx.archi:strict-databinding:3.3.2-beta2'
//可选
implementation 'com.kunminx.archi:binding-adapter:3.3.2-beta2'
implementation 'com.kunminx.archi:binding-recyclerview:3.3.2-beta3'
```

> **温馨提示：**
>
> 在使用 “DataBinding 严格模式” 后，对于 “属性动画” 等 “对视图实例强依赖” 的场景，可借助 “Motion 动画” 等新式框架代替（确定 Motion 动画的学习成本不足属性动画的 20%，且效果好、收益高，具体视频教程可见我们在[《MotionChallenge》](https://github.com/Jetpack-Missionary/MotionChallenge)的分享）。
>
> 如对 Jetpack Compose 基于函数式编程思想 “解决视图调用一致性问题” 的理论基础感兴趣，可详见[《事关软件工程安全 的 数据驱动 UI 框架 扫盲干货》](https://xiaozhuanlan.com/topic/2356748910)的铺垫，此处不做累述。


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