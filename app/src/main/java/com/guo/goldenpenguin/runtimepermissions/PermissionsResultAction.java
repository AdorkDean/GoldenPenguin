/**
 * Copyright 2015 Anthony Restaino

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific language governing
 permissions and limitations under the License.
 */
package com.guo.goldenpenguin.runtimepermissions;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *将实例传递给 requestpermissionsifnecessaryforresult方法。结  *果将被送回你无论是ongranted（所有的权限已被授予），或ondenied（所需*的权限被拒绝）。
 *你把你的ongranted方法和通知功能,用户什么都在ondenied方法不工作。
 */
public abstract class PermissionsResultAction {

  private static final String TAG = PermissionsResultAction.class.getSimpleName();
  private final Set<String> mPermissions = new HashSet<String>(1);
  private Looper mLooper = Looper.getMainLooper();

  /**
   * Default Constructor
   */
  public PermissionsResultAction() {}

  /**
   *如果您是在后台线程中使用权限请求，则希望
   *回调是在UI线程中,通过looper刷新ui
   */
  @SuppressWarnings("unused")
  public PermissionsResultAction(@NonNull Looper looper) {mLooper = looper;}

  /**
   *当所有权限已被用户授予,把所有你的权限敏感的代码，可以只需执行所                        需权限
   */
  public abstract void onGranted();

  /**
   *当一个权限被拒绝的时候调用
   *
   * @param permission 被拒绝的权限
   */
  public abstract void onDenied(String permission);

  /**
   * 忽视未发现的权限
   */
  @SuppressWarnings({"WeakerAccess", "SameReturnValue"})
  public synchronized boolean shouldIgnorePermissionNotFound(String permission) {
    Log.d(TAG, "Permission not found: " + permission);
    return true;
  }
  /*
      *返回授权 结果
      */
  @SuppressWarnings("WeakerAccess")
  @CallSuper
  protected synchronized final boolean onResult(final @NonNull String permission, int result) {
    if (result == PackageManager.PERMISSION_GRANTED) {
      return onResult(permission, Permissions.GRANTED);
    } else {
      return onResult(permission, Permissions.DENIED);
    }

  }

  /**
   *当一个特定的权限已更改。
   *此方法将调用所有权限，所以该方法确定
   *如果授权影响到该状态，是否可以继续进行
   */
  @SuppressWarnings("WeakerAccess")
  @CallSuper
  protected synchronized final boolean onResult(final @NonNull String permission, Permissions result) {
    //先从权限列表里移除当前权限
    mPermissions.remove(permission);
    if (result == Permissions.GRANTED) {
      if (mPermissions.isEmpty()) {
        new Handler(mLooper).post(new Runnable() {
          @Override
          public void run() {
            onGranted();
          }
        });
        return true;
      }
    } else if (result == Permissions.DENIED) {//权限被拒
      new Handler(mLooper).post(new Runnable() {
        @Override
        public void run() {
          onDenied(permission);
        }
      });
      return true;
    } else if (result == Permissions.NOT_FOUND) {
      if (shouldIgnorePermissionNotFound(permission)) {
        if (mPermissions.isEmpty()) {//权限为空
          new Handler(mLooper).post(new Runnable() {
            @Override
            public void run() {
              onGranted();
            }
          });//去授权
          return true;
        }
      } else {
        new Handler(mLooper).post(new Runnable() {
          @Override
          public void run() {
            onDenied(permission);
          }
        }); //拒绝权限
        return true;
      }
    }
    return false;
  }

  /**
   *注册指定的权限对象的permissionsresultaction
   *让它知道哪些权限来查找更改。
   *
   * @param perms permissions名单
   */
  @SuppressWarnings("WeakerAccess")
  @CallSuper
  protected synchronized final void registerPermissions(@NonNull String[] perms) {
    Collections.addAll(mPermissions, perms);
  }
}