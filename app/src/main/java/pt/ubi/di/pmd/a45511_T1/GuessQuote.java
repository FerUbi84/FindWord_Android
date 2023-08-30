package pt.ubi.di.pmd.a45511_T1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class GuessQuote extends AppCompatActivity {
    LinearLayout linear_guess_1;
    LinearLayout linear_guess_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_quote);

        linear_guess_1 = findViewById(R.id.linear_guess_1);
        linear_guess_2 = findViewById(R.id.linear_guess_2);
    }
}