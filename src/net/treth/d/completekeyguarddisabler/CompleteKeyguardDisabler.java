package net.treth.d.completekeyguarddisabler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XC_MethodReplacement;

public class CompleteKeyguardDisabler implements IXposedHookLoadPackage {

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// look for the lockscreens
		if (lpparam.packageName.contains("com.android.keyguard")) {
			// Now inside the 4.3+ keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [4.4]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedHelpers.findAndHookMethod(
				"com.android.keyguard.KeyguardViewMediator", 
				lpparam.classLoader,
				"doKeyguardLocked", "android.os.Bundle",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not locking the screen");
						return null;
					}
				}
			);
		}
		// need testers for this
		else if (lpparam.packageName.contains("com.android.internal.policy.impl.keyguard")) {
			// Now inside the 4.2-4.3 keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [4.2-4.3]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedHelpers.findAndHookMethod(
				"com.android.internal.policy.impl.keyguard.KeyguardViewMediator", 
				lpparam.classLoader,
				"doKeyguardLocked", "android.os.Bundle",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not locking the screen");
						return null;
					}
				}
			);
		}
		// need testers for this
		else if (lpparam.packageName.contains("com.android.internal.policy.impl")) {
			// Now inside the < 4.2 keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [?-4.1.2]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedHelpers.findAndHookMethod(
				"com.android.internal.policy.impl.KeyguardViewMediator", 
				lpparam.classLoader,
				"doKeyguardLocked",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not locking the screen");
						return null;
					}
				}
			);
		}
		return;
		// use this to add more keyguards
		/* 
		else if (lpparam.packageName.contains("com.htc.lockscreen")) {
			// Now inside the HTC keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [HTC]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedHelpers.findAndHookMethod(
				"com.htc.lockscreen.HtcKeyguardViewMediator", 
				lpparam.classLoader,
				"doKeyguardLocked", "android.os.Bundle",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not locking the screen");
						return null;
					}
				}
			);
		}*/
	}
}
