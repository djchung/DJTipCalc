package com.bluekeyllc.djtipcalc.activities;

import com.bluekeyllc.djtipcalc.R;
import com.bluekeyllc.djtipcalc.R.id;
import com.bluekeyllc.djtipcalc.R.layout;
import com.bluekeyllc.djtipcalc.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
		mEtTotalBill.setFocusable(true);
		mEtTotalBill.setSelection(mEtTotalBill.getText().length());
		mEtTotalBill.addTextChangedListener(new TextWatcher() {
			//TODO prepend $
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if(mEtTotalBill.length() > 0) {
					if(!String.valueOf(s.charAt(0)).equals("$")) {
						if(String.valueOf(s).contains("$")) {
							String totalBillString = mEtTotalBill.getText().toString();
							int index = totalBillString.lastIndexOf("$");
							Log.d("tag", String.valueOf(index));
							totalBillString = totalBillString.substring(index + 1);
							mEtTotalBill.setText(totalBillString);
							Log.d("tag", mEtTotalBill.getText().toString());
						}  else {
							mEtTotalBill.setText(getBillAsDollarString());
						}
						mEtTotalBill.setSelection(mEtTotalBill.length());
					} else {
						if(mEtTotalBill.length() == 1) {
							mEtTotalBill.setText("");
						} 
						
					}
				}
				
				onRadioButtonClicked(mRadioButtonSelected);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mTvTip = (TextView) findViewById(R.id.tvTip);
		mEtCustomTip = (EditText) findViewById(R.id.etCustomTip);
		mEtCustomTip.setFocusable(true);
		mEtCustomTip.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mRadioGroup.check(R.id.customRadio);
					onRadioButtonClicked(mCustomRadioButton);
					mEtCustomTip.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							
							mRadioGroup.check(R.id.customRadio);
							if(mEtCustomTip.length() > 0) {
								String percentString = String.valueOf(s);
								if(!String.valueOf(s.charAt(s.length() - 1)).equals("%")) {
									if (percentString.contains("%")) {
										int index = percentString.indexOf("%");
										percentString = percentString.substring(0, index);
									}
									mEtCustomTip.setText(getTipAsPercentage(percentString));
								} else {
									if (mEtCustomTip.length() == 1) {
										mEtCustomTip.setText("");
									}
									
								}								
							} 
						
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
	
	private String getTipAsPercentage(String s) {
		return s + "%";
	}
	
	private double getBillAmount() {
		double billAmount = Double.parseDouble(removeDollarSign(mEtTotalBill.getText().toString()));
		return billAmount;
	}
	
	private void showTipAmount(double tipPercentage, double billAmount) {
		mTipAmount = getTipAmount(tipPercentage, billAmount);
		mTvTip.setText("$" + String.valueOf(mTipAmount));	
	}
	
	private String removeDollarSign(String s) {
		
		if(String.valueOf(s.charAt(0)).equals("$")) {
			s = s.substring(1);
		}
		return s;
	}
	
	private double getTipAmount (double tipPercentage, double billAmount) {
		return billAmount * tipPercentage / 100.0;
	}
	
	private double getCustomTipPercentage() {
		
		String customTipString = mEtCustomTip.getText().toString();
		if(customTipString.contains("%")) {
			int index = customTipString.lastIndexOf("%");
			customTipString = customTipString.substring(0, index);
		}
		return Double.parseDouble(customTipString);
	}
	
	public void onRadioButtonClicked(View view) {
		
		//TODO on select, dismiss keyboard?
		TextView tvTotalBill = (TextView) findViewById(R.id.totalBill);
		mRadioButtonSelected = (RadioButton) view;
		if (mEtTotalBill.getText().toString().trim().isEmpty() || mEtTotalBill.getText().toString().equals("$")) {
			mTvTip.setText("");
			tvTotalBill.setText("");
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
					if (!mEtCustomTip.getText().toString().trim().isEmpty()) {
						mEtCustomTip.setSelection(mEtCustomTip.getText().length() - 1);
						mCustomTipPercentage = getCustomTipPercentage();
						showTipAmount(mCustomTipPercentage, mBillAmount);
					} else {
						mTvTip.setText("");
						mTipAmount = 0;
					}
						
				break;
			
			}
			
			double totalBill = mTipAmount + getBillAmount();
			
			tvTotalBill.setText("$" + String.valueOf(totalBill));
			
		}
	
	}

}
