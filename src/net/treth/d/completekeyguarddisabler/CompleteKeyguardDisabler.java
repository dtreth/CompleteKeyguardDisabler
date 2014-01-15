package net.treth.d.completekeyguarddisabler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XC_MethodReplacement;


public class CompleteKeyguardDisabler implements IXposedHookLoadPackage {
	
	public final boolean SUPERDEBUG = false;

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// look for the lockscreens
		if (lpparam.packageName.contains("com.android.keyguard")) {
			// Now inside the 4.3+ keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [4.4]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedBridge.log("hooking method doKeyguardLocked in class 'com.android.keyguard.KeyguardViewMediator'");
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
			// hook the KeyguardViewMediator adjustStatusBarLocked method
			XposedBridge.log("hooking method adjustStatusBarLocked in class 'com.android.keyguard.KeyguardViewMediator'");
			XposedHelpers.findAndHookMethod(
				"com.android.keyguard.KeyguardViewMediator", 
				lpparam.classLoader,
				"adjustStatusBarLocked",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not adjusting the statusbar (and navigation bar) [hopefully]");
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
			XposedBridge.log("hooking method doKeyguardLocked in class 'com.android.internal.policy.impl.keyguard.KeyguardViewMediator'");
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
			// hook the KeyguardViewMediator adjustStatusBarLocked method
			XposedBridge.log("hooking method adjustStatusBarLocked in class 'com.android.internal.policy.impl.keyguard.KeyguardViewMediator'");
			XposedHelpers.findAndHookMethod(
				"com.android.internal.policy.impl.keyguard.KeyguardViewMediator", 
				lpparam.classLoader,
				"adjustStatusBarLocked",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not adjusting the statusbar (and navigation bar) [hopefully]");
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
			XposedBridge.log("hooking method doKeyguardLocked in class 'com.android.internal.policy.impl.keyguard.KeyguardViewMediator'");
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
			// hook the KeyguardViewMediator adjustStatusBarLocked method
			XposedBridge.log("hooking method adjustStatusBarLocked in class 'com.android.internal.policy.impl.KeyguardViewMediator'");
			XposedHelpers.findAndHookMethod(
				"com.android.internal.policy.impl.KeyguardViewMediator", 
				lpparam.classLoader,
				"adjustStatusBarLocked",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not adjusting the statusbar (and navigation bar) [hopefully]");
						return null;
					}
				}
			);
		}
		// Experimental HTC support 
		else if (lpparam.packageName.contains("com.htc.lockscreen")) {
			// Now inside the HTC keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [HTC]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedBridge.log("hooking method doKeyguardLocked in class 'com.htc.lockscreen.HtcKeyguardViewMediator'");
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
			// hook the KeyguardViewMediator adjustStatusBarLocked method
			XposedBridge.log("hooking method adjustStatusBarLocked in class 'com.htc.lockscreen.HtcKeyguardViewMediator'");
			XposedHelpers.findAndHookMethod(
				"com.htc.lockscreen.HtcKeyguardViewMediator", 
				lpparam.classLoader,
				"adjustStatusBarLocked",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not adjusting the statusbar (and navigation bar) [hopefully]");
						return null;
					}
				}
			);
		}
		/*// Experimental Samsung support 
		else if (lpparam.packageName.contains("com.sec.android.app.keyguard")) {
			// Now inside the Samsung keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [Samsung]");
			// hook the KeyguardViewMediator doKeyguardLocked method
			XposedBridge.log("hooking method doKeyguardLocked in class 'com.sec.android.app.keyguard'");
			XposedHelpers.findAndHookMethod(
				"com.sec.android.app.keyguard.KeyguardViewMediator", 
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
			// hook the KeyguardViewMediator adjustStatusBarLocked method
			XposedBridge.log("hooking method adjustStatusBarLocked in class 'com.sec.android.app.keyguard'");
			XposedHelpers.findAndHookMethod(
				"com.sec.android.app.keyguard.KeyguardViewMediator", 
				lpparam.classLoader,
				"adjustStatusBarLocked",
				new XC_MethodReplacement() {
					// do a full replacement of the method
					@Override
					protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
						// do nothing, but log it
						XposedBridge.log("Not adjusting the statusbar (and navigation bar) [hopefully]");
						return null;
					}
				}
			);
		}*/
		// Experimental SUPERDEBUG 
		/*else if (SUPERDEBUG && (lpparam.packageName.contains("lock") || lpparam.packageName.contains("guard"))) {
			XposedBridge.log("Package may be a keyguard " + lpparam.packageName + " [HYPERDEBUG]");
		}*/
	}
}
