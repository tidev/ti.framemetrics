Titanium.UI.setBackgroundColor('#fff');

var FrameMetrics = require('ti.framemetrics');

// create tab group
var tabGroup = Titanium.UI.createTabGroup();

var logger = function(context, framemetrics) {
	Ti.API.info('-------------' + context + '--------------');
	Ti.API.info('Animation duration:' + framemetrics.getMetric(FrameMetrics.ANIMATION_DURATION));
	Ti.API.info('Command issue duration:' + framemetrics.getMetric(FrameMetrics.COMMAND_ISSUE_DURATION));
	Ti.API.info('Draw duration:' + framemetrics.getMetric(FrameMetrics.DRAW_DURATION));
	Ti.API.info('Is this the first drawn frame:' + framemetrics.getMetric(FrameMetrics.FIRST_DRAW_FRAME));
	Ti.API.info('Input handling duration:' + framemetrics.getMetric(FrameMetrics.INPUT_HANDLING_DURATION));
	Ti.API.info('Intended VSYNC timestamp:' + framemetrics.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP));
	Ti.API.info('Layout measure duration:' + framemetrics.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION));
	Ti.API.info('Swap buffers duration:' + framemetrics.getMetric(FrameMetrics.SWAP_BUFFERS_DURATION));
	Ti.API.info('Sync duration:' + framemetrics.getMetric(FrameMetrics.SYNC_DURATION));
	Ti.API.info('Total duration:' + framemetrics.getMetric(FrameMetrics.TOTAL_DURATION));
	Ti.API.info('Unknown delay duration:' + framemetrics.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION));
	Ti.API.info('VSYNC timestamp:' + framemetrics.getMetric(FrameMetrics.VSYNC_TIMESTAMP));
}

tabGroup.addEventListener('open', function() {
	FrameMetrics.start(this, function(e) {
		logger('TAB_GROUP', e.framemetrics);
	});
});

tabGroup.addEventListener('close', function() {
	FrameMetrics.stop(this);
});

//
// create base UI tab and root window
//
var win1 = Titanium.UI.createWindow({  
	title:'Tab 1',
	backgroundColor:'#fff'
});

var newWindowButton = Ti.UI.createButton({title: 'New window'});
newWindowButton.addEventListener('click', function(e){
	var newWindow = Ti.UI.createWindow({backgroundColor: 'red'});
	newWindow.addEventListener('open', function(e) {
		FrameMetrics.start(this, function(e) {
			logger('NEW_WINDOW', e.framemetrics);
		});
	});
	newWindow.addEventListener('close', function() {
		FrameMetrics.stop(this);
	});
	var clickButton = Ti.UI.createButton({title: 'Click'});
	newWindow.add(clickButton);
	newWindow.open();
});
win1.add(newWindowButton);
var tab1 = Titanium.UI.createTab({  
	title:'Tab 1',
	window:win1
});
//
// create controls tab and root window
//
var win2 = Titanium.UI.createWindow({  
	title:'Tab 2',
	backgroundColor:'#fff'
});
win2.add(Ti.UI.createButton({title: 'Click'}));
var tab2 = Titanium.UI.createTab({  
	title:'Tab 2',
	window:win2
});
//
//  add tabs
//
tabGroup.addTab(tab1);  
tabGroup.addTab(tab2);  


// open tab group
tabGroup.open();
