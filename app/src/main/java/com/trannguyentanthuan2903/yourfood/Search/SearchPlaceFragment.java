package com.trannguyentanthuan2903.yourfood.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfood.Profile.ProfileActivity;
import com.trannguyentanthuan2903.yourfood.R;
import com.trannguyentanthuan2903.yourfood.Utils.PhotoListAdapter;
import com.trannguyentanthuan2903.yourfood.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 10/5/2017.
 */

public class SearchPlaceFragment extends Fragment {
    //widgets
    private EditText mSearchParam;
    private ListView mListView;

    //vars
    private List<Photo> mPhotoList;
    private PhotoListAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_place, container, false);
        mSearchParam = (EditText) view.findViewById(R.id.search);
        mListView = (ListView) view.findViewById(R.id.listView);


        initTextListener();
        updatePhotosList();


        return view;
    }



    private void initTextListener() {
        Log.d(TAG, "initTextListener: initializing");

        mPhotoList = new ArrayList<>();

        mSearchParam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());
                searchForMatch(text);
            }
        });
    }

    private void searchForMatch(String keyword) {
        Log.d(TAG, "searchForMatch: searching for a match: " + keyword);
        mPhotoList.clear();
        //update the users list view
        if (keyword.length() == 0) {

        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child(getString(R.string.dbname_photos))
                    .orderByChild(getString(R.string.field_tag)).equalTo("#" + keyword);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue(Photo.class).toString());

                        mPhotoList.add(singleSnapshot.getValue(Photo.class));
                        //update the users list view
                        updatePhotosList();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void updatePhotosList() {
        Log.d(TAG, "updateUsersList: updating users list");

        mAdapter = new PhotoListAdapter(getActivity(), R.layout.layout_photo_listitem, mPhotoList);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected user: " + mPhotoList.get(position).toString());

                //navigate to profile activity
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.search_activity));
                intent.putExtra(getString(R.string.intent_user), mPhotoList.get(position));
                startActivity(intent);
            }
        });
    }

//    databaseReference.orderByChild('_searchLastName')
//            .startAt(queryText)
//                 .endAt(queryText+"\uf8ff")
//                 .once("value")

}
