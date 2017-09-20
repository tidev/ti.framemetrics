/**
 * Axway Appcelerator Titanium - ti.touchid
 * Copyright (c) 2017 by Axway. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.framemetrics;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.FrameMetrics;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;

import ti.modules.titanium.TitaniumModule;

@Kroll.proxy
public class FrameInfoProxy extends KrollProxy {

	private FrameMetrics frameMetrics;

	public FrameInfoProxy(FrameMetrics source) {
		frameMetrics = source;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Kroll.method
	public long getMetric(int id) {
		return frameMetrics.getMetric(id);
	}

	@Override
	public String getApiName()
	{
		return "Ti.FrameInfo";
	}

}