package com.bluekeyllc.djtipcalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TipActivity extends Activity {
	private EditText mEtTotalBill;
	private TextView mTvTip;
	private RadioButton mRadioButtonSelected;
	private RadioButton mCustomRadioButton;
	private RadioGroup mRadioGroup;
	private EditText mEtCustomTip;
	private double mBillAmount;
	private double mTipAmount;
	private double mCustomTipPercentage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		mRadioGroup.check(R.id.radio10);
		int checkedId = mRadioGroup.getCheckedRadioButtonId();
		mRadioButtonSelected = (RadioButton) findViewById(checkedId);
		mCustomRadioButton = (RadioButton) findViewById(R.id.customRadio);
		
		mEtTotalBill = (EditText) findViewById(R.id.etTotalBill);
		mEtTotalBill.setSelection(mEtTotalBill.getText().length());
		mEtTotalBill.addTextChangedListener(new TextWatcher() {
			//TODO prepend $
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				onRadioButtonClicked(mRadioButtonSelected);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
//				if(s.charAt(0) != '$') {
//					mBillString = getBillAsDollarString();
//					mEtTotalBill.setText(mBillString);
//				}
				
			}
		});
		
		mTvTip = (TextView) findViewById(R.id.tvTip);
		mEtCustomTip = (EditText) findViewById(R.id.etCustomTip);
		mEtCustomTip.setFocusable(true);
		
		//TODO prepend $
		mEtCustomTip.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mRadioGroup.check(R.id.customRadio);
				onRadioButtonClicked(mCustomRadioButton);
				
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
		
		mEtCustomTip.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mRadioGroup.check(R.id.customRadio);
					onRadioButtonClicked(mCustomRadioButton);
					mEtCustomTip.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							onRadioButtonClicked(mCustomRadioButton);
							
						}
						
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count,
								int after) {
							
						}
						
						@Override
						public void afterTextChanged(Editable s) {
							
						}
					});
				}
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip, menu);
		return true;
	}
	
	private String getBillAsDollarString() {
		return "$" + mEtTotalBill.getText().toString(); 
	}
	
	private double getBillAmount() {
		double billAmount = Double.parseDouble(mEtTotalBill.getText().toString());
		return billAmount;
	}
	
	private void showTipAmount(double tipPercentage, double billAmount) {
		mTipAmount = getTipAmount(tipPercentage, billAmount);
		mTvTip.setText(String.valueOf(mTipAmount));	
	}
	
	private double getTipAmount (double tipPercentage, double billAmount) {
		return billAmount * tipPercentage / 100.0;
	}
	/*
	private void showNoBillAmountDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(TipActivity.this);
		builder.setMessage("Please type in the bill amount").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.create().show();
	}
	*/
	
	private double getCustomTipPercentage() {
		return Double.parseDouble(mEtCustomTip.getText().toString());
	}
	
	public void onRadioButtonClicked(View view) {
		
		//TODO on select, dismiss keyboard?
		
		mRadioButtonSelected = (RadioButton) view;
		if (mEtTotalBill.getText().toString().trim().isEmpty() || mEtTotalBill.getText().toString() == "$" ) {
			mTvTip.setText("");
			mTipAmount = 0;
				
		} else {
			
			mBillAmount = getBillAmount();
			boolean checked = mRadioButtonSelected.isChecked();
			
			switch(view.getId()) {
			
			case R.id.radio10:
				if (checked)
					mEtCustomTip.clearFocus();
					showTipAmount(10, mBillAmount);
				break;
			case R.id.radio15:
				if (checked)
					mEtCustomTip.clearFocus();
					showTipAmount(15, mBillAmount);
				break;
			case R.id.radio20:
				if (checked)
					mEtCustomTip.clearFocus();
					showTipAmount(20, mBillAmount);
				break;
			case R.id.customRadio:
				if (checked)
					mEtCustomTip.setSelection(mEtCustomTip.getText().length());
					if (!mEtCustomTip.getText().toString().trim().isEmpty()) {
						mCustomTipPercentage = getCustomTipPercentage();
						showTipAmount(mCustomTipPercentage, mBillAmount);
					} else {
						mTvTip.setText("");
						mTipAmount = 0;
					}
						
				break;
			
			}
			
			double totalBill = mTipAmount + getBillAmount();
			
			TextView tvTotalBill = (TextView) findViewById(R.id.totalBill);
			tvTotalBill.setText("$" + String.valueOf(totalBill));
			
		}
	
	}

}
