package com.bluekeyllc.djtipcalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TipActivity extends Activity {
	private EditText mEtTotalBill;
	private TextView mTvTip;
	private RadioButton mRadioButtonSelected;
	private double mBillAmount;
	private double mTipAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		int checkedId = radioGroup.getCheckedRadioButtonId();
		mRadioButtonSelected = (RadioButton) findViewById(checkedId);
		
		mEtTotalBill = (EditText) findViewById(R.id.etTotalBill);
		mEtTotalBill.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onRadioButtonClicked(mRadioButtonSelected);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
		
		mRadioButtonSelected = (RadioButton) view;
		if (mEtTotalBill.getText().toString().trim().isEmpty()) {
			mTvTip.setText("");
				
		} else {
			
			mBillAmount = getBillAmount();
			boolean checked = mRadioButtonSelected.isChecked();
			
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
