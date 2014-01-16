package net.treth.d.completekeyguarddisabler;

import java.util.Arrays;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XC_MethodReplacement;


public class CompleteKeyguardDisabler implements IXposedHookLoadPackage {
	
	public final boolean SUPERDEBUG = false;

	@SuppressWarnings("unused")
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		// look for the lockscreen
		if (lpparam.packageName.contains("com.android.keyguard")) {
			// Now inside the 4.3+ keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [4.4]");
			// hook the KeyguardViewMediator showLocked method
			mockOutMethod("Not locking the screen",
						  lpparam.classLoader,
						  "com.android.keyguard.KeyguardViewMediator",
						  "showLocked",
						  "android.os.Bundle");			
		}
		// need testers for this
		else if (lpparam.packageName.contains("com.android.internal.policy.impl.keyguard")) {
			// Now inside the 4.2-4.3 keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [4.2-4.3]");
			// hook the KeyguardViewMediator showLocked method
			mockOutMethod("Not locking the screen",
					  	  lpparam.classLoader, 
						  "com.android.internal.policy.impl.keyguard.KeyguardViewMediator",
						  "showLocked",
						  "android.os.Bundle");			
		}
		// need testers for this
		else if (lpparam.packageName.contains("com.android.internal.policy.impl")) {
			// Now inside the < 4.2 keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [?-4.1.2]");
			// hook the KeyguardViewMediator showLocked method
			mockOutMethod("Not locking the screen",
						  lpparam.classLoader, 
					  	  "com.android.internal.policy.impl.KeyguardViewMediator",
					  	  "showLocked");			
		}
		// Experimental HTC support 
		else if (lpparam.packageName.contains("com.htc.lockscreen")) {
			// Now inside the HTC keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [HTC]");
			// hook the KeyguardViewMediator showLocked method
			mockOutMethod("Not locking the screen",
					  lpparam.classLoader, 
					  "com.htc.lockscreen.HtcKeyguardViewMediator",
					  "showLocked", "android.os.Bundle");
		}
		// Experimental Samsung support 
		/*
		else if (lpparam.packageName.contains("com.sec.android.app.keyguard")) {
			// Now inside the Samsung keyguard
			XposedBridge.log("In package " + lpparam.packageName + " [Samsung]");
			// hook the KeyguardViewMediator showLocked method
			mockOutMethod("Not locking the screen",
					  lpparam.classLoader, 
					  "com.sec.android.app.keyguard.KeyguardViewMediator",
					  "showLocked", "android.os.Bundle");
		} 
		*/
		// Hopefully this is the root of the navbar issue
		else if (lpparam.packageName.contains("com.android.systemui")) {
			XposedBridge.log("Trying special ActiveDisplay nuke");
			// look for the ActiveDisplay element causing the problem
			mockOutMethod("Not allowing ActiveDisplay to change the system bars",
					  lpparam.classLoader, 
				  	  "com.android.systemui.statusbar.policy.activedisplay.ActiveDisplayView",
				  	  "adjustStatusBarLocked");
		}
		// Experimental SUPERDEBUG 
		else if (SUPERDEBUG && (lpparam.packageName.contains("lock") || lpparam.packageName.contains("guard"))) {
			XposedBridge.log("Package may be a keyguard " + lpparam.packageName + " [SUPERDEBUG]");
		}
	}

	private boolean mockOutMethod(final String logMessage,
								  ClassLoader cl,
								  final String className, 
								  final String methodName, 
								  Object... parameterTypes) {
		try {
			XposedBridge.log("hooking method '" + methodName + "' in class '" + className + "'");
			// create the XCMethodReplacement
			XC_MethodReplacement mockOut = new XC_MethodReplacement() {
				// do a full replacement of the method
				@Override
				protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
					// do nothing, but log it
					XposedBridge.log(logMessage);
					return null;
				}
			};
			// build the parameter list
			Object[] parameterTypesAndCallback = Arrays.copyOf(parameterTypes, parameterTypes.length + 1);
			parameterTypesAndCallback[parameterTypes.length] = mockOut;
			// actually hook the method
			XposedHelpers.findAndHookMethod(
				className, 
				cl,
				methodName, 
				parameterTypesAndCallback);
		}
		catch (Throwable t) {
			// log the Throwable if the method hook fails
			XposedBridge.log(t);
			XposedBridge.log("hook method failed: '" + className + "." + methodName + "'");
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private boolean traceMethod(ClassLoader cl,
								final String className,
								final String methodName,
								Object... parameterTypes) {
		try {
			XposedBridge.log("hooking method '" + methodName + "' in class '" + className + "'");
			// create the XCMethodHook
			XC_MethodHook stackTraceHook = new XC_MethodHook() {
		    	@Override
		    	protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		    		// try to see what is calling the method
		    		XposedBridge.log("" + methodName + " was called!");
		    		logStackTrace(Thread.currentThread().getStackTrace());
		    	}
			};
			// build the parameter list
			Object[] parameterTypesAndCallback = Arrays.copyOf(parameterTypes, parameterTypes.length + 1);
			parameterTypesAndCallback[parameterTypes.length] = stackTraceHook;
			// actually hook the method
			XposedHelpers.findAndHookMethod(
				className,
				cl,
				methodName,
				parameterTypesAndCallback);
		}
		catch (Throwable t) {
			// log the Throwable if the method hook fails
			XposedBridge.log(t);
			XposedBridge.log("hook method failed: '" + className + "." + methodName + "'");
			return false;
		}
		return true;
	}

	private void logStackTrace(StackTraceElement[] stackTraceElements) {
		XposedBridge.log("Got StackTrace");
		for (int i = 0; i < stackTraceElements.length; i++)
		{
			XposedBridge.log(" Caller " + i);
			XposedBridge.log("  Class: " + stackTraceElements[i].getClassName());
			XposedBridge.log("    Method: " + stackTraceElements[i].getMethodName());
			XposedBridge.log("      File: " + stackTraceElements[i].getFileName());
			XposedBridge.log("      Line: " + stackTraceElements[i].getLineNumber());
		}
	}
}
