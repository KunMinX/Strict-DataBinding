package com.kunminx.architecture.ui.state;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * Create by KunMinX at 2022/5/30
 */
public class State<T> extends ObservableField<T> {

  private final boolean mIsDebouncing;

  /**
   * 务必根据泛型提供初值，以彻底规避 Null 安全问题
   * Be sure to provide initial values based on generics to completely avoid null security issues
   *
   * @param value initial value
   */
  public State(@NonNull T value) {
    this(value, false);
  }

  public State(@NonNull T value, boolean isDebouncing) {
    super(value);
    mIsDebouncing = isDebouncing;
  }

  /**
   * 如果 isDebouncing = true 开启防抖，那么只在值变化时通知刷新。默认总是通知刷新
   * 如果注册 DiffCallback，在值变化时可做一些操作
   * @param value set value
   * @param callback diff callback
   */
  public void set(@NonNull T value, DiffCallback<T> callback) {
    boolean isUnChanged = get() == value;
    super.set(value);
    if (!mIsDebouncing && isUnChanged) notifyChange();
    if (!isUnChanged && callback != null) callback.onDiff(value);
  }

  @Override
  public void set(@NonNull T value) {
    set(value, null);
  }

  public interface DiffCallback<T> {
    void onDiff(T value);
  }
}
