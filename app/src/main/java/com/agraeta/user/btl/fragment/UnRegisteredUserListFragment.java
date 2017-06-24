package com.agraeta.user.btl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.TourActivity;
import com.agraeta.user.btl.adapters.UnRegisteredUserTourAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.UnregisteredUserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnRegisteredUserListFragment extends Fragment {

    EditText edt_search;
    ImageView img_search;
    ListView listUnRegisteredUser;
    TourActivity tourActivity;
    Custom_ProgressDialog dialog;
    AdminAPI adminAPI;
    UnRegisteredUserTourAdapter unRegisteredUserTourAdapter;

    List<UnregisteredUserData.UserData> userDataList = new ArrayList<>();

    public UnRegisteredUserListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_un_registered_user_list, container, false);

        tourActivity = (TourActivity) getActivity();
        dialog = new Custom_ProgressDialog(getActivity(), "");
        dialog.setCancelable(false);

        unRegisteredUserTourAdapter = new UnRegisteredUserTourAdapter(userDataList, getActivity());

        Log.e("StateID", tourActivity.stateID);

        edt_search = (EditText) view.findViewById(R.id.edt_search);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        listUnRegisteredUser = (ListView) view.findViewById(R.id.listUnRegisteredUser);
        listUnRegisteredUser.setAdapter(unRegisteredUserTourAdapter);
        adminAPI = ServiceGenerator.getAPIServiceClass();
        dialog.show();
        Call<UnregisteredUserData> tourResponseCall = adminAPI.unregisteredUserTourCall(tourActivity.stateID, null);
        tourResponseCall.enqueue(new Callback<UnregisteredUserData>() {
            @Override
            public void onResponse(Call<UnregisteredUserData> call, Response<UnregisteredUserData> response) {
                dialog.dismiss();
                userDataList.clear();
                UnregisteredUserData tourResponse = response.body();
                if (tourResponse != null) {
                    if (tourResponse.isStatus()) {
                        userDataList.addAll(tourResponse.getData());
                    } else {
                        Globals.Toast2(getContext(), tourResponse.getMessage());
                    }
                } else {
                    Globals.defaultError(getContext());
                }

                unRegisteredUserTourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<UnregisteredUserData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t, getContext());
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchKey = edt_search.getText().toString();
                Call<UnregisteredUserData> tourResponseCall;
                if (searchKey.isEmpty()) {
                    tourResponseCall = adminAPI.unregisteredUserTourCall(tourActivity.stateID, null);
                } else {
                    tourResponseCall = adminAPI.unregisteredUserTourCall(tourActivity.stateID, searchKey);
                }
                tourResponseCall.enqueue(new Callback<UnregisteredUserData>() {
                    @Override
                    public void onResponse(Call<UnregisteredUserData> call, Response<UnregisteredUserData> response) {
                        dialog.dismiss();
                        userDataList.clear();
                        UnregisteredUserData tourResponse = response.body();
                        if (tourResponse != null) {
                            if (tourResponse.isStatus()) {
                                userDataList.addAll(tourResponse.getData());
                            } else {
                                Globals.Toast2(getContext(), tourResponse.getMessage());
                            }
                        } else {
                            Globals.defaultError(getContext());
                        }

                        unRegisteredUserTourAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<UnregisteredUserData> call, Throwable t) {
                        dialog.dismiss();
                        Globals.showError(t, getContext());
                    }
                });
            }
        });

        return view;
    }

}
