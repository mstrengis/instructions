package lv.androiddev.instructions;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;

public class Instruction {

    private String text;

    public Instruction setText(Context context, @StringRes int text) {
        this.text = context.getString(text);
        return this;
    }

    public Instruction setText(String text) {
        this.text = text;
        return this;
    }

    public void showFor(Activity activity, View view) {
        Instruction.clear(activity);
        InstructionBackground.showFor(activity, view);
        InstructionBalloon.showFor(activity, view, text);
    }

    public static boolean isVisible(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        return InstructionBackground.isVisible(parent);
    }

    public static void clear(View instruction) {
        ViewGroup parent = (ViewGroup) instruction.getParent();
        InstructionBalloon.clear(parent);
        InstructionBackground.clear(parent);
    }

    public static void clear(Activity activity) {
        if (activity != null) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            InstructionBalloon.clear(parent);
            InstructionBackground.clear(parent);
        }
    }
}
