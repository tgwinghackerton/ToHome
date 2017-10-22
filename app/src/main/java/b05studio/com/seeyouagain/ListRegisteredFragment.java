package b05studio.com.seeyouagain;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mansu on 2017-10-21.
 */

public class ListRegisteredFragment extends Fragment {
    @BindView(R.id.list_registered_recyclerview)
    RecyclerView listRegisteredRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_list_registered, container, false);
        ButterKnife.bind(this, view);

        ListRegisteredAdapter adapter = new ListRegisteredAdapter(getActivity());
        HashMap<String, MissingPersonInfo> exampleInfos = new HashMap<>();
        adapter.setMissingPersonInfos(exampleInfos);
        listRegisteredRecyclerView.setAdapter(adapter);
        getMissingPersonInfos(adapter);
        return view;
    }

    public void getMissingPersonInfos(final ListRegisteredAdapter adapter) {
        FirebaseDatabase.getInstance().getReference().child("mpi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, MissingPersonInfo> missingPersonInfos = new HashMap<String, MissingPersonInfo>();//HashMap<String, MissingPersonInfo>)dataSnapshot.getValue(); <- 이렇게해서 그냥 adapter set하면안된다??
                //getValue&#xb85c; &#xd074;&#xb798;&#xc2a4; &#xc9c0;&#xc815;&#xc548;&#xd574;&#xc8fc;&#xba74; &#xb9f5;&#xc73c;&#xb85c;&#xbc1b;&#xc544;&#xc62c;&#xb54c; &#xadf8; &#xd074;&#xb798;&#xc2a4;&#xc778;&#xc9c0;&#xbab0;&#xb77c;&#xc11c; &#xbcc0;&#xacbd;&#xc548;&#xc2dc;&#xcf1c;&#xc918;&#xc11c; &#xbb38;&#xc81c;&#xc0dd;&#xae34;&#xb2e4;&#xb4e0;&#xb370;?
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MissingPersonInfo info = dataSnapshot1.getValue(MissingPersonInfo.class);
                    if(info.isAccepted())
                        missingPersonInfos.put(dataSnapshot1.getKey(), info);
                }
                adapter.setMissingPersonInfos(missingPersonInfos);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO 2017-08-10: 실패했을때 메시지 어떻게 띄울것인지
            }
        });
    }

    @OnClick(R.id.list_registered_fab)
    public void fabClick(View view) {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }
}
