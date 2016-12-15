package shbd.addressselect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import shbd.addresspickerview.AddressPickView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textview);
        mTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        AddressPickView addressPickView = new AddressPickView(this);
        addressPickView.show(mTextView);
    }
}
