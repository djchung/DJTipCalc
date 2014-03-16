package com.bluekeyllc.djtipcalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class TipActivity extends Activity {
	private EditText mEtTotalBill;
	private TextView mTvTip;
	private double mBillAmount;
	private double mTipAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		
		//TODO member variable convention?
		mEtTotalBill = (EditText) findViewById(R.id.etTotalBill);
		mTvTip = (TextView) findViewById(R.id.tvTip);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip, menu);
		return true;
	}
	
	private double getBillAmount() {
		double billAmount = Double.parseDouble(mEtTotalBill.getText().toString());
		return billAmount;
	}
	
	private void showTipAmount(double tipPercentage, double billAmount) {
		mTipAmount = billAmount * tipPercentage / 100.0;
		mTvTip.setText(String.valueOf(mTipAmount));	
	}
	
	private void showNoBillAmountDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(TipActivity.this);
		builder.setMessage("Please type in the bill amount").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.create().show();
	}
	
	public void onRadioButtonClicked(View view) {
		
		if (mEtTotalBill.getText().toString().trim().isEmpty()) {
			showNoBillAmountDialog();
				
		} else {
			
			mBillAmount = getBillAmount();
			boolean checked = ((RadioButton) view).isChecked();
			
			switch(view.getId()) {
			
			case R.id.radio10:
				if (checked)
					showTipAmount(10, mBillAmount);
				break;
			case R.id.radio15:
				if (checked)
					showTipAmount(15, mBillAmount);
				break;
			case R.id.radio20:
				if (checked)
					showTipAmount(20, mBillAmount);
				break;
			
			}
			
		}
	
	}

}
