package lv.androiddev.instructions;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

class InstructionBackground extends FrameLayout {

    private static final String BACKGROUND_TAG = "instruction_background";

    public InstructionBackground(Context context) {
        super(context);
    }

    public static void showFor(Activity activity, View view) {
        InstructionBackground instructionBackground = new InstructionBackground(activity);
        instructionBackground.setTag(BACKGROUND_TAG);
        ViewGroup root = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        int[] location = new int[2];
        view.getLocationInWindow(location);
        View top = prepareBackgroundItem(activity);
        View left = prepareBackgroundItem(activity);
        View right = prepareBackgroundItem(activity);
        View bottom = prepareBackgroundItem(activity);
        instructionBackground.addView(top, generateTopParams(root, location));
        instructionBackground.addView(bottom, generateBottomParams(root, view, location));
        instructionBackground.addView(left, generateLeftParams(view, location));
        instructionBackground.addView(right, generateRightParams(view, location));
        root.addView(instructionBackground);
        instructionBackground.setAlpha(0);
        instructionBackground.animate().alpha(1);
    }

    private static LayoutParams generateLeftParams(View highlightView, int[] location) {
        LayoutParams result = new LayoutParams(location[0], highlightView.getHeight());
        result.topMargin = location[1];
        return result;
    }

    private static LayoutParams generateRightParams(View highlightView, int[] location) {
        LayoutParams result = new LayoutParams(LayoutParams.MATCH_PARENT, highlightView.getHeight());
        result.topMargin = location[1];
        result.leftMargin = location[0] + highlightView.getWidth();
        return result;
    }

    private static LayoutParams generateBottomParams(ViewGroup root, View highlightView, int[] location) {
        LayoutParams result = new LayoutParams(root.getWidth(), LayoutParams.MATCH_PARENT);
        result.topMargin = location[1] + highlightView.getHeight();
        return result;
    }

    private static LayoutParams generateTopParams(ViewGroup root, int[] location) {
        LayoutParams result = new LayoutParams(root.getWidth(), location[1]);
        return result;
    }

    public static View prepareBackgroundItem(Context context) {
        View backgroundItem = new View(context);
        backgroundItem.setBackgroundColor(ContextCompat.getColor(backgroundItem.getContext(), R.color.instructionBackground));
        backgroundItem.setOnClickListener(new BackgroundItemClickListener());
        return backgroundItem;
    }

    private static final class BackgroundItemClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            Instruction.clear((View) view.getParent());
        }
    }

    public static boolean isVisible(ViewGroup root) {
        return root.findViewWithTag(BACKGROUND_TAG) != null;
    }

    public static void clear(ViewGroup root) {
        for (int i = 0; i < 4; i++) {
            root.removeView(root.findViewWithTag(BACKGROUND_TAG));
        }
    }
}
