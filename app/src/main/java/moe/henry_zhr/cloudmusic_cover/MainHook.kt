package moe.henry_zhr.cloudmusic_cover

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class MainHook : IXposedHookLoadPackage {
  override fun handleLoadPackage(lpparam: LoadPackageParam) {
    if (lpparam.packageName != CLOUDMUSIC_PACKAGE_NAME)
      return
    val clazz = lpparam.classLoader.loadClass("android.view.View")
    XposedHelpers.findAndHookMethod(
      clazz,
      "setRotation",
      Float::class.java,
      object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam) {
          super.beforeHookedMethod(param)
          param.args[0] = 0.0f
        }
      })
  }

  companion object {
    private const val CLOUDMUSIC_PACKAGE_NAME = "com.netease.cloudmusic"
  }
}