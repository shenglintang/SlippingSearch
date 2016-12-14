package com.example.slippingsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.slippingsearch.SideBar.OnTouchingLetterChangedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	/**
	 * 显示字母的TextView
	 */
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		SourceDateList = new ArrayList<SortModel>();
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sideBar.setTextView(dialog);
		for (int i = 0; i < 20; i++) {
			SortModel item = new SortModel();
			switch (i) {
			case 0:

				item.setName("老虎");
				break;
			case 1:

				item.setName("小狗");
				break;
			case 2:

				item.setName("公鸡");
				break;
			case 3:

				item.setName("鱼");
				break;
			case 4:

				item.setName("狮子");
				break;
			case 5:

				item.setName("蛇");
				break;
			case 6:

				item.setName("猫头鹰");
				break;
			case 7:

				item.setName("猫咪");
				break;
			case 8:

				item.setName("老师");
				break;
			case 9:

				item.setName("老鹰");
				break;
				
			case 10:
				
				item.setName("小狗");
				break;
			case 11:
				
				item.setName("公鸡");
				break;
			case 12:
				
				item.setName("鱼");
				break;
			case 13:
				
				item.setName("鱼");
				break;
			case 14:
				
				item.setName("狮子");
				break;
			case 15:
				
				item.setName("蛇");
				break;
			case 16:
				
				item.setName("猫头鹰");
				break;
			case 17:
				
				item.setName("猫咪");
				break;
			case 18:
				
				item.setName("老师");
				break;
			case 19:
				
				item.setName("老鹰");
				break;

			}

			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(item.getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				item.setSortLetters(sortString.toUpperCase());
			} else {
				item.setSortLetters("#");
			}
			SourceDateList.add(item);
		}
		adapter = new SortAdapter(this, SourceDateList, true);
		sortListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		sortListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		// 设置右侧触摸监听显示字母
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});
		// 搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
			// SourceDateList = filterDateList;
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
