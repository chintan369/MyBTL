package com.agraeta.user.btl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.agraeta.user.btl.adapters.RegisteredUserTourAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.RegisteredUserTourResponse;
import com.agraeta.user.btl.model.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredUserListFragment extends Fragment {

    EditText edt_search;
    ImageView img_search;
    ListView listRegisteredUser;
    TourActivity tourActivity;
    Custom_ProgressDialog dialog;
    AdminAPI adminAPI;
    RegisteredUserTourAdapter unRegisteredUserTourAdapter;

    List<RegisteredUserTourResponse.RegisteredUserTour> userDataList = new ArrayList<>();

    public RegisteredUserListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_registered_user_list, container, false);
        View view=inflater.inflate(R.layout.fragment_registered_user_list, container, false);
        tourActivity = (TourActivity) getActivity();
        dialog = new Custom_ProgressDialog(getActivity(), "");
        dialog.setCancelable(false);
        unRegisteredUserTourAdapter = new RegisteredUserTourAdapter(userDataList, getActivity());

        edt_search = (EditText) view.findViewById(R.id.edt_search);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        listRegisteredUser = (ListView) view.findViewById(R.id.listRegisteredUser);
        listRegisteredUser.setAdapter(unRegisteredUserTourAdapter);
        adminAPI = ServiceGenerator.getAPIServiceClass();
        dialog.show();
        Call<RegisteredUserTourResponse> tourResponseCall = adminAPI.userTourResponseCall(tourActivity.stateID, null);
        tourResponseCall.enqueue(new Callback<RegisteredUserTourResponse>() {
            @Override
            public void onResponse(Call<RegisteredUserTourResponse> call, Response<RegisteredUserTourResponse> response) {
                dialog.dismiss();
                userDataList.clear();
                RegisteredUserTourResponse tourResponse = response.body();
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
            public void onFailure(Call<RegisteredUserTourResponse> call, Throwable t) {
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
                Call<RegisteredUserTourResponse> tourResponseCall;
                if (searchKey.isEmpty()) {
                    tourResponseCall = adminAPI.userTourResponseCall(tourActivity.stateID, null);
                } else {
                    tourResponseCall = adminAPI.userTourResponseCall(tourActivity.stateID, searchKey);
                }
                tourResponseCall.enqueue(new Callback<RegisteredUserTourResponse>() {
                    @Override
                    public void onResponse(Call<RegisteredUserTourResponse> call, Response<RegisteredUserTourResponse> response) {
                        dialog.dismiss();
                        userDataList.clear();
                        RegisteredUserTourResponse tourResponse = response.body();
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
                    public void onFailure(Call<RegisteredUserTourResponse> call, Throwable t) {
                        dialog.dismiss();
                        Globals.showError(t, getContext());
                    }
                });
            }
        });
        return view;
    }

}
