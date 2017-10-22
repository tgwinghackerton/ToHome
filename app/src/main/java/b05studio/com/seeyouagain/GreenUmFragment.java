package b05studio.com.seeyouagain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import b05studio.com.seeyouagain.model.GreenUmInfo;
import b05studio.com.seeyouagain.model.MissingPersonInfo;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mansu on 2017-10-21.
 */

public class GreenUmFragment extends Fragment {
    @BindView(R.id.list_greenum_recyclerview)
    RecyclerView listGreenUmRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_list_greenum, container, false);
        ButterKnife.bind(this, view);

        GreenUmAdapter adapter = new GreenUmAdapter(getActivity());
        HashMap<String, GreenUmInfo> exampleInfos = new HashMap<>();
        adapter.setGreenUmInfos(exampleInfos);
        listGreenUmRecyclerView.setAdapter(adapter);
        getGreenUmInfos(adapter);
        return view;
    }

    public void getGreenUmInfos(final GreenUmAdapter adapter) {
        FirebaseDatabase.getInstance().getReference().child("missingChilds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, GreenUmInfo> greenUmInfos = new HashMap<String, GreenUmInfo>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    GreenUmInfo info = dataSnapshot1.getValue(GreenUmInfo.class);
                    greenUmInfos.put(dataSnapshot1.getKey(), info);
                }
                adapter.setGreenUmInfos(greenUmInfos);
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

}
