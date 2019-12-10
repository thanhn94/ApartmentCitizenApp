package com.example.apartmentcitizen.home.account.wallet;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.apartmentcitizen.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class WalletActivity extends AppCompatActivity {

    CardView walletCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setUpView();

    }

    public void setUpView() {
        ViewTreeObserver treeObserver = findViewById(R.id.constraint_layout_wallet_activity).getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                walletCardView = findViewById(R.id.card_wallet);

                float height = walletCardView.getHeight();
                Animation animation = new TranslateAnimation(0, 0, (0f - height), 0f);

                animation.setDuration(400);
                walletCardView.startAnimation(animation);
            }
        });
    }

    public void clickToPayNow(View view) {
    }

    public void clickToRecharge(View view) {
    }

    public void clickToShowHistoryTrans(View view) {
    }
}
