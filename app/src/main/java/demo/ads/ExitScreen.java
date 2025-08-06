package demo.ads;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.smartclick.auto.tap.autoclicker.R;

public class ExitScreen extends AppCompatActivity {
    private ImageView txt_no;
    private ImageView txt_rate;
    private ImageView txt_yes;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.exit_screen);
        this.txt_rate = findViewById(R.id.txt_rate);
        this.txt_yes = findViewById(R.id.txt_yes);
        this.txt_no = findViewById(R.id.txt_no);
        this.txt_rate.setOnClickListener(new ExitScreen1(this));
        this.txt_yes.setOnClickListener(new ExitScreen2(this));
        this.txt_no.setOnClickListener(new ExitScreen3(this));
        GoogleAds.getInstance().addNativeView(this, findViewById(R.id.nativeLay));
    }

      public /* synthetic */ void rateOnClickListener(View view) {
        rate();
    }

     public /* synthetic */ void yesOnClickListener(View view) {
        yes();
    }

      public /* synthetic */ void noOnClickListener(View view) {
        no();
    }

    private void rate() {
        AppUtil.rateApp(this);
    }

    private void yes() {
        finishAffinity();
    }

    private void no() {
        onBackPressed();
    }
}
