package lv.androiddev.instructions;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

class InstructionBalloon {

    private static final String BALLOON_TAG = "instruction_balloon";
    private static final String ARROW_TAG = "instruction_arrow";

    public static void showFor(Activity activity, View child, String text) {
        ViewGroup root = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        View balloon = setupBalloon(activity, text);
        View arrow = setupArrow(activity);
        root.addView(balloon, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(arrow, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        balloon.measure(0, 0);
        translateBalloon(root, balloon, arrow, child);
    }

    private static void translateBalloon(ViewGroup root, View balloon, View arrow, View child) {
        int[] childPosition = new int[2];
        child.getLocationInWindow(childPosition);
        int arrowHeight = DimenUtils.dp(11);
        balloon.setTranslationY(childPosition[1] + child.getHeight() + arrowHeight);
        int minBalloonX = DimenUtils.dp(10);
        int ballonTranslateX = Math.max(minBalloonX, childPosition[0] + (child.getWidth() / 2) - balloon.getMeasuredWidth() / 2);
        if (ballonTranslateX + balloon.getMeasuredWidth() > root.getWidth()) {
            ballonTranslateX += (root.getWidth() - (ballonTranslateX + balloon.getMeasuredWidth()));
            ballonTranslateX -= DimenUtils.dp(10);
        }
        balloon.setTranslationX(ballonTranslateX);
        arrow.setTranslationY(childPosition[1] + child.getHeight());
        arrow.setTranslationX(childPosition[0] + child.getWidth() / 2 - DimenUtils.dp(10));
        if (balloon.getMeasuredHeight() + balloon.getTranslationY() > DimenUtils.getScreenHeight()) {
            balloon.setPivotY(balloon.getMeasuredHeight());
            translateBallonToChildUp(balloon, arrow, child);
        } else {
            balloon.setPivotY(0);
        }
        balloon.setPivotX(balloon.getMeasuredWidth() / 2);
        balloon.setScaleX(0);
        balloon.setScaleY(0);
        balloon.animate().scaleX(1).scaleY(1);
    }

    private static void translateBallonToChildUp(View balloon, View arrow, View child) {
        int[] childPosition = new int[2];
        child.getLocationInWindow(childPosition);
        int arrowHeight = DimenUtils.dp(11);
        arrow.setTranslationY(childPosition[1] - arrowHeight);
        arrow.setRotation(180);
        balloon.setTranslationY(childPosition[1] - arrowHeight - balloon.getMeasuredHeight());
    }

    private static ImageView setupArrow(Activity activity) {
        ImageView arrow = new ImageView(activity);
        arrow.setTag(ARROW_TAG);
        arrow.setImageResource(R.drawable.balloon_arrow);
        return arrow;
    }

    private static TextView setupBalloon(Activity activity, String text) {
        TextView balloon = new TextView(activity);
        balloon.setTag(BALLOON_TAG);
        balloon.setText(text);
        balloon.setTextColor(ContextCompat.getColor(activity, R.color.instructionTextColor));
        int dp10 = DimenUtils.dp(10);
        balloon.setPadding(dp10, dp10, dp10, dp10);
        balloon.setTextSize(14);
        balloon.setGravity(Gravity.CENTER);
        balloon.setBackgroundResource(R.drawable.balloon_background);
        balloon.setMaxWidth(DimenUtils.dp(300));
        balloon.setOnClickListener(new BalloonClickListener());
        return balloon;
    }

    public static void clear(ViewGroup root) {
        root.removeView(root.findViewWithTag(BALLOON_TAG));
        root.removeView(root.findViewWithTag(ARROW_TAG));
    }

    private static final class BalloonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Instruction.clear(view);
        }
    }
}
