/**
 * Axway Appcelerator Titanium - ti.touchid
 * Copyright (c) 2017 by Axway. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.framemetrics;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.proxy.ActivityProxy;
import org.appcelerator.titanium.proxy.TiWindowProxy;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.view.FrameMetrics;
import android.view.Window;

@Kroll.module(name="FrameMetrics", id="ti.framemetrics")
public class FrameMetricsModule extends KrollModule
{
	@Kroll.constant public final static String PROPERTY_FRAME_METRICS = "framemetrics";

	@Kroll.constant public final static int ANIMATION_DURATION = FrameMetrics.ANIMATION_DURATION;
	@Kroll.constant public final static int COMMAND_ISSUE_DURATION = FrameMetrics.COMMAND_ISSUE_DURATION;
	@Kroll.constant public final static int DRAW_DURATION = FrameMetrics.DRAW_DURATION;
	@Kroll.constant public final static int	FIRST_DRAW_FRAME = FrameMetrics.FIRST_DRAW_FRAME;
	@Kroll.constant public final static int	INPUT_HANDLING_DURATION = FrameMetrics.INPUT_HANDLING_DURATION;
	@Kroll.constant public final static int	INTENDED_VSYNC_TIMESTAMP = FrameMetrics.INTENDED_VSYNC_TIMESTAMP;
	@Kroll.constant public final static int	LAYOUT_MEASURE_DURATION = FrameMetrics.LAYOUT_MEASURE_DURATION;
	@Kroll.constant public final static int	SWAP_BUFFERS_DURATION = FrameMetrics.SWAP_BUFFERS_DURATION;
	@Kroll.constant public final static int	SYNC_DURATION = FrameMetrics.SYNC_DURATION;
	@Kroll.constant public final static int	TOTAL_DURATION = FrameMetrics.TOTAL_DURATION;
	@Kroll.constant public final static int	UNKNOWN_DELAY_DURATION = FrameMetrics.UNKNOWN_DELAY_DURATION;
	@Kroll.constant public final static int	VSYNC_TIMESTAMP = FrameMetrics.VSYNC_TIMESTAMP;

	private Window.OnFrameMetricsAvailableListener frameMetricsAvailableListener;

	public FrameMetricsModule()
	{
		super();
	}

	@Kroll.method
	public void start(TiWindowProxy window, final KrollFunction callback) {
		ActivityProxy activityProxy = window.getWindowActivityProxy();
		if (activityProxy != null) {
			Activity activity = activityProxy.getActivity();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity != null) {
				frameMetricsAvailableListener = new Window.OnFrameMetricsAvailableListener() {
					@Override
					public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int i) {
						KrollDict dictionary = new KrollDict();
						dictionary.put(PROPERTY_FRAME_METRICS, new FrameInfoProxy(frameMetrics));
						callback.callAsync(getKrollObject(), dictionary);
					}
				};
				activity.getWindow().addOnFrameMetricsAvailableListener(frameMetricsAvailableListener, new Handler());
			}
		}
	}

	@Kroll.method
	public void stop(TiWindowProxy window) {
		ActivityProxy activityProxy = window.getWindowActivityProxy();
		if (activityProxy != null) {
			Activity activity = activityProxy.getActivity();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity != null) {
				activity.getWindow().removeOnFrameMetricsAvailableListener(frameMetricsAvailableListener);
			}
		}
	}
	
}