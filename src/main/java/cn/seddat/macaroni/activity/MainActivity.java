package cn.seddat.macaroni.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.seddat.macaroni.MacaroniApplication;
import cn.seddat.macaroni.R;
import cn.seddat.macaroni.content.ToastService;

import com.baidu.mobstat.StatService;

public class MainActivity extends TabActivity {

	private static final String tag = MainActivity.class.getSimpleName();
	private String appName;

	private TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.main);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title);
		// title
		appName = getApplicationInfo().loadLabel(getPackageManager()).toString();
		// 百度移动统计
		// StatService.setDebugOn(true);
		StatService.setLogSenderDelayed(3);// 启动后延迟3s发送统计日志
		// tab widget
		tabHost = getTabHost();
		final int defaultTab = 0;
		final String[] tags = new String[] { "call", "sms", "trace" };
		final int[] labels = new int[] { R.string.main_tab_call, R.string.main_tab_sms, R.string.main_tab_trace };
		final Class<?>[] tagets = new Class<?>[] { CallActivity.class, CallActivity.class, CallActivity.class };
		final Resources res = getResources();
		final LayoutInflater inflater = LayoutInflater.from(this);
		for (int i = 0, size = tags.length; i < size; i++) {
			View tab = inflater.inflate(R.layout.main_tab_indicator, getTabWidget(), false);
			TextView title = (TextView) tab.findViewById(android.R.id.title);
			title.setText(res.getString(labels[i]));
			if (i == defaultTab) {
				title.setTextColor(res.getColor(R.color.main_tab_selected));
			}
			TabHost.TabSpec tabSpec = tabHost.newTabSpec(tags[i]).setIndicator(tab)
					.setContent(new Intent().setClass(this, tagets[i]));
			tabHost.addTab(tabSpec);
		}
		tabHost.setCurrentTab(defaultTab);
		tabHost.setOnTabChangedListener(new MainTabChangeListener());
	}

	class MainTabChangeListener implements OnTabChangeListener {

		@Override
		public void onTabChanged(String tabId) {
			// 切换背景色
			Resources res = getResources();
			for (int i = 0, size = tabHost.getTabWidget().getChildCount(), current = tabHost.getCurrentTab(); i < size; i++) {
				final View tab = tabHost.getTabWidget().getChildTabViewAt(i);
				final TextView title = (TextView) tab.findViewById(android.R.id.title);
				final int color = i != current ? R.color.main_tab_default : R.color.main_tab_selected;
				title.setTextColor(res.getColor(color));
			}
			// 统计各个Tab点击情况
			String tabName = null;
			if ("call".equalsIgnoreCase(tabId)) {
				tabName = getResources().getString(R.string.main_tab_call);
			} else if ("sms".equalsIgnoreCase(tabId)) {
				tabName = getResources().getString(R.string.main_tab_sms);
			} else if ("trace".equalsIgnoreCase(tabId)) {
				tabName = getResources().getString(R.string.main_tab_trace);
			} else {
				tabName = "unknown";
			}
			StatService.onEvent(MainActivity.this, "tabs", tabName, 1);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// stats
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// stats
		StatService.onPause(this);
	}

	private long backTime = 0;

	@Override
	public void onBackPressed() {
		long curTime = System.currentTimeMillis();
		if (curTime - backTime > 2000) {
			backTime = curTime;
			ToastService.toast(this, "再按一次退出" + appName, Toast.LENGTH_SHORT);
		} else {
			this.addShortcut();
			super.onBackPressed();
		}
	}

	private void addShortcut() {
		SharedPreferences pref = getSharedPreferences(MacaroniApplication.class.getSimpleName(), Context.MODE_PRIVATE);
		if (pref.getBoolean("shortcut-installed", false)) {
			return;
		}
		Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(this, R.drawable.icon));
		intent.putExtra(
				Intent.EXTRA_SHORTCUT_INTENT,
				new Intent(this, MainActivity.class).setAction("android.intent.action.MAIN").addCategory(
						"android.intent.category.LAUNCHER"));
		intent.putExtra("duplicate", false);
		this.sendBroadcast(intent);
		Editor editor = pref.edit();
		editor.putBoolean("shortcut-installed", true);
		editor.commit();
		Log.i(tag, "install shortcut for " + appName);
	}

}