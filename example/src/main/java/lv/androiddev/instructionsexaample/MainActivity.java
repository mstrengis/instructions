package lv.androiddev.instructionsexaample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lv.androiddev.instructions.Instruction;
import lv.androiddev.instructionsexaample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.hideTextView.setOnClickListener(new HideTextViewClick());
        binding.showTextView.setOnClickListener(new ShowTextViewClick());
        showInstruction();
    }

    private void showInstruction(){
        binding.showTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Instruction()
                        .setText(getString(R.string.instructionFirstText))
                        .showFor(MainActivity.this, binding.hideTextView);
            }
        }, 300);
    }

    @Override
    public void onBackPressed() {
        if (Instruction.isVisible(this)) {
            Instruction.clear(this);
        } else {
            super.onBackPressed();
        }
    }

    private final class ShowTextViewClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            binding.textView.setVisibility(View.VISIBLE);
            Instruction.clear(MainActivity.this);
        }
    }

    private final class HideTextViewClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            binding.textView.setVisibility(View.GONE);
            Instruction.clear(MainActivity.this);
            new Instruction()
                    .setText(getString(R.string.clickThisButtonToShowTextView))
                    .showFor(MainActivity.this, binding.showTextView);
        }
    }
}
