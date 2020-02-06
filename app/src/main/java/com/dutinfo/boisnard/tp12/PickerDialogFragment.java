package com.dutinfo.boisnard.tp12;

import android.widget.EditText;

import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;
import mobi.upod.timedurationpicker.TimeDurationUtil;

/**
 * Duration Picker dialog
 */
public class PickerDialogFragment extends TimeDurationPickerDialogFragment {

    private final int maxDigits = 6;
    private final StringBuilder input = new StringBuilder(maxDigits);

    @Override
    protected long getInitialDuration() {
        return 15 * 60 * 1000;
    }


    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.HH_MM;
    }

    @Override
    public void onDurationSet(TimeDurationPicker view, long duration) {
        EditText et = getActivity().findViewById(R.id.dureeId);
        setDuration(duration);
        et.setText(input);

    }

    private void setDuration(long millis) {
        setDuration(
                TimeDurationUtil.hoursOf(millis),
                TimeDurationUtil.minutesInHourOf(millis),
                TimeDurationUtil.secondsInMinuteOf(millis));
    }

    private void setDuration(long hours, long minutes, long seconds) {
        if (hours > 99 || minutes > 99)
            setDurationString("99", "99", "99");
        else
            setDurationString(stringFragment(hours), stringFragment(minutes), stringFragment(seconds));
    }

    private void setDurationString(String hours, String minutes, String seconds) {
        input.setLength(0);
        input.append(hours);
        input.append(":");
        input.append(minutes);
        input.append(":");
        input.append(seconds);
    }

    private String stringFragment(long value) {
        return (value < 10 ? "0" : "") + value;
    }
}