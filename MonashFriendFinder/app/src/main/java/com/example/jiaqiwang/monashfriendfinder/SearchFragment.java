package com.example.jiaqiwang.monashfriendfinder;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class SearchFragment extends Fragment {
    View vSearch;

    private Button findButton;
    private MultiSpinner multiSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vSearch = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        return vSearch;
    }

    public void init(){
        multiSpinner = (MultiSpinner) vSearch.findViewById(R.id.mulSpinner);
        multiSpinner.setTitle("Select Attributes");
        final ArrayList multiSpinnerList=new ArrayList();
        SimpleSpinnerOption option1=new SimpleSpinnerOption();
        option1.setName("Gender");
        option1.setValue(1);
        multiSpinnerList.add(option1);

        SimpleSpinnerOption option2=new SimpleSpinnerOption();
        option2.setName("Course");
        option2.setValue(2);
        multiSpinnerList.add(option2);

        SimpleSpinnerOption option3=new SimpleSpinnerOption();
        option3.setName("Study Mode");
        option3.setValue(3);
        multiSpinnerList.add(option3);

        SimpleSpinnerOption option4=new SimpleSpinnerOption();
        option4.setName("Suburb");
        option4.setValue(4);
        multiSpinnerList.add(option4);

        SimpleSpinnerOption option5=new SimpleSpinnerOption();
        option5.setName("Nationality");
        option5.setValue(5);
        multiSpinnerList.add(option5);

        SimpleSpinnerOption option6=new SimpleSpinnerOption();
        option6.setName("Native Language");
        option6.setValue(6);
        multiSpinnerList.add(option6);

        SimpleSpinnerOption option7=new SimpleSpinnerOption();
        option7.setName("Favourite Sport");
        option7.setValue(7);
        multiSpinnerList.add(option7);

        SimpleSpinnerOption option8=new SimpleSpinnerOption();
        option8.setName("Favourite Unit");
        option8.setValue(8);
        multiSpinnerList.add(option8);

        multiSpinner.setDataList(multiSpinnerList);

        findButton = (Button) vSearch.findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiSpinner.getText().equals("Please select attributes you want")){
                    Toast.makeText(vSearch.getContext(), "Please select attributes you want then Go to search", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(vSearch.getContext(),FindFriendActivity.class);
                    String[] flag = multiSpinner.getText().toString().split(",");
                    String keywordList = multiSpinner.getText().toString();
                    intent.putExtra("flag",flag);
                    intent.putExtra("keywordList", keywordList);
                    vSearch.getContext().startActivity(intent);;
                }
            }
        });
    }

}