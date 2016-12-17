package shbd.addressselect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import shbd.addresspickerview.AddressPickView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddressPickView.OnPickerClickListener {
    private TextView mTextView;
    AddressPickView addressPickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textview);
        mTextView.setOnClickListener(this);
        addressPickView = new AddressPickView(this);
        addressPickView.setOnPickerClickListener(this);

    }

    @Override
    public void onClick(View view) {
        addressPickView.show(mTextView);
    }

    @Override
    public void onPickerClick(String selectData, String zipCode) {
        mTextView.setText(selectData + ":" + zipCode);
    }
}
