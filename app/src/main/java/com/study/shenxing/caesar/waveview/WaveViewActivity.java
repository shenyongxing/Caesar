package com.study.shenxing.caesar.waveview;

/**
 * Created by shenxing on 15/12/6.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.study.shenxing.caesar.R;

public class WaveViewActivity extends Activity {

    private SeekBar seekBar;
    private WaveView waveView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_view_layout);

        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        waveView = (WaveView) findViewById(R.id.wave_view);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}