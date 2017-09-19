package com.agraeta.user.btl;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.CompanySalesPerson.UserTypeActivity;
import com.agraeta.user.btl.DisSalesPerson.SalesTypeActivity;
import com.agraeta.user.btl.admin.AdminDashboard;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LogInPage extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    EditText et_mobile, et_password;
    Button btn_login;
    TextView btn_forgot_password, btn_register;
    String loginid = new String();
    String password = new String();
    String json = new String();
    Custom_ProgressDialog loadingView;
    String st_response = new String();
    AppPrefs apps;
    String email = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    // Google client to interact with Google API
    GoogleApiClient mGoogleApiClient;
    String social_login_id = new String();
    DatabaseHandler db;
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private SignInButton btnSignIn;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(LogInPage.this);
        callbackManager = CallbackManager.Factory.create();

        getFbKeyHash("com.agraeta.user.btl");

        setContentView(R.layout.activity_log_in_page);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LoginManager.getInstance().logOut();

        setActionBar();
        fetchID();

    }

    private void fetchID() {
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot_password = (TextView) findViewById(R.id.btn_forgot_password);
        btn_register = (TextView) findViewById(R.id.btn_register);


        //fb
        loginButton = (LoginButton) findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override


            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
// handle error
                                } else {
                                    apps = new AppPrefs(LogInPage.this);
                                    apps.setUser_LoginWith("1");
                                    String email = me.optString("email");
                                    String first_name = me.optString("first_name");
                                    String last_name = me.optString("last_name");
                                    //System.out.println("11111111111111111111111");
                                    //System.out.println(me);
                                    //Log.e("first_name", "" + first_name);
                                    //Log.e("last_name", "" + last_name);
                                    //Log.e("123", "" + me);
                                    apps = new AppPrefs(LogInPage.this);
                                    String s = me.toString().replaceAll("////", "");
                                    //Log.e("fb Response", "" + s);
                                    apps.setUser_SocialReInfo("" + s);
                                    apps.setUser_SocialFirst(first_name);
                                    apps.setUser_Sociallast(last_name);


                                    //Log.e("EmailId", "" + email);


// Intent i = new Intent(this, SignUpActivity.class);
                                    //startActivity(new Intent(LogInPage.this, Business_Registration.class).putExtra("json", me.toString()));
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields",
                        "id,name, email, birthday,gender,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

                /*//Log.e("info text", "" +
                                "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
                apps = new AppPrefs(LogInPage.this);

                apps.setUser_SocialIdInfo(loginResult.getAccessToken().getUserId());

                social_login_id = loginResult.getAccessToken().getUserId();

               /* info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/

                //System.out.println("Facebook Login Successful!");
                //System.out.println("Logged in user Details : ");
                //System.out.println("--------------------------");

                //System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
                //System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
                // Toast.makeText(LogInPage.this, "Login Successful!" + loginResult.getAccessToken().getUserId() + "-----" + loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG).show();

                // Toast.makeText(Facebook_Activity.this,"Successfully Login----------"+loginResult.toString(),Toast.LENGTH_LONG).show();

                new send_LoginSocial_Data().execute();

            }

            @Override
            public void onCancel() {
                //  info.setText("Login attempt canceled.");
                //Log.e("12", "Login attempt canceled.");
                // Toast.makeText(Facebook_Activity.this,"cancel Login",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                // info.setText("Login attempt failed."+""+e.toString());
                //Log.e("13", "Login attempt failed..");
                //Log.e("Errorrrr", "" + e.toString());
                //Toast.makeText(Facebook_Activity.this,"Error Login----------"+e.toString(),Toast.LENGTH_LONG).show();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();
        //G+
        btnSignIn = (SignInButton) findViewById(R.id.btn_google_signin);
        setGooglePlusButtonText(btnSignIn, "Sign in with Google");
        btnSignIn.setSize(SignInButton.SIZE_WIDE);
        // btnSignIn.setForegroundGravity(View.TEXT_ALIGNMENT_GRAVITY);
        btnSignIn.setScopes(gso.getScopeArray());
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apps = new AppPrefs(LogInPage.this);
                apps.setUser_LoginWith("2");
                if (!mGoogleApiClient.isConnecting()) {
                    mSignInClicked = true;
                    resolveSignInError();
                }
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(LogInPage.this)
                .addConnectionCallbacks(LogInPage.this)
                .addOnConnectionFailedListener(LogInPage.this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        //-------------------------------------------------------------------------
        //G+
//        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                apps = new AppPrefs(LogInPage.this);
//                apps.setUser_LoginWith("2");
//                if (!mGoogleApiClient.isConnecting()) {
//                    mSignInClicked = true;
//                    resolveSignInError();
//                }
//            }
//        });
//
//        mGoogleApiClient = new GoogleApiClient.Builder(LogInPage.this)
//                .addConnectionCallbacks(LogInPage.this)
//                .addOnConnectionFailedListener(LogInPage.this).addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

//-------------------------------------------------------------------------------------------------

     /*   mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .addScope(new Scope(Scopes.PLUS_ME))
                .build();
        mGoogleApiClient.connect();*/

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(LogInPage.this, "Mobile/Email is required", getLayoutInflater());
                    et_mobile.requestFocus();
                    //Toast.makeText(LogInPage.this, "Please enter login id", Toast.LENGTH_LONG).show();
                } else if (et_password.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(LogInPage.this, "Password is required", getLayoutInflater());
                    et_password.requestFocus();
                    //Toast.makeText(LogInPage.this,"Please enter password",Toast.LENGTH_LONG).show();
                } else if (et_password.length() < 6) {
                    Globals.CustomToast(LogInPage.this, "Password minimum 6 character required", getLayoutInflater());
                    et_password.requestFocus();
                    // Toast.makeText(LogInPage.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();
                } else {

                    loginid = et_mobile.getText().toString().trim();
                    password = et_password.getText().toString().trim();


                    new send_Login_Data().execute();
                }
               /* Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });
        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(LogInPage.this, Forgot_password.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(LogInPage.this);
                apps.setUser_LoginWith("0");
                Intent i = new Intent(LogInPage.this, Business_Registration.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    //google method
    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                tv.setText("Sign in with Google" + "\th");
                return;
            }
        }
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.clearDefaultAccountAndReconnect();
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {

        //Log.e("requestCode", "" + requestCode);
        //Log.e("responseCode", "" + responseCode);
        super.onActivityResult(requestCode, responseCode, intent);
        switch (requestCode) {

            case 64206:

                callbackManager.onActivityResult(requestCode, responseCode, intent);
                break;

            case 0:

                if (requestCode == RC_SIGN_IN) {

                    if (responseCode != RESULT_OK) {
                        mSignInClicked = false;
                    }

                    mIntentInProgress = false;

                    if (!mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                }
                break;

            default:
                if (requestCode == RC_SIGN_IN) {

                    if (responseCode != RESULT_OK) {
                        mSignInClicked = false;
                    }

                    mIntentInProgress = false;

                    if (!mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                }
                break;

        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        //Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        Globals.CustomToast(LogInPage.this, "User is connected!", getLayoutInflater());
        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        updateUI(true);

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            // new send_LoginSocial_Data().execute();

        } else {
            btnSignIn.setVisibility(View.VISIBLE);

        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {


                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);

                //Log.e("Google Name",currentPerson.getDisplayName()+"-->"+currentPerson.getImage().getUrl()+"-->"+Plus.AccountApi.getAccountName(mGoogleApiClient)+"-->\n"+currentPerson.getUrl()+" --> "+currentPerson.getId());

                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = "https://plus.google.com/" + currentPerson.getId();
                //Log.e("version",""+ Build.VERSION.SDK_INT);
                if (Build.VERSION.SDK_INT >= 23) {

                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, 1);
                        return;
                    }
                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    //email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                } else {

                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                }
                //String email = "a@a.com";
                //Log.e("1", "" + email);
                /*//Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);*/
                JSONArray jObjectMain = new JSONArray();

                for (int i = 0; i < 1; i++) {
                    JSONObject jObjectData = new JSONObject();

                    jObjectData.put("Name", personName);
                    jObjectData.put("plusProfile", personGooglePlusProfile);
                    jObjectData.put("email", email);
                    jObjectData.put("Image", personPhotoUrl);

                    jObjectMain.put(jObjectData);
                    st_response = jObjectMain.toString();

                }

                //System.out.println("st_response" + st_response);
                //Log.e("st_response", "" + st_response);

                apps = new AppPrefs(LogInPage.this);
                apps.setUser_SocialReInfo(st_response);

                String CurrentStr = personGooglePlusProfile;

                Log.e("personProfile", personGooglePlusProfile);

                //String[] separated1 = CurrentStr.split("/");
                String cr = currentPerson.getId();
                //Log.e("Social_Id",cr);
                social_login_id = cr;
                apps.setUser_SocialIdInfo(cr);
                String CurrentString = currentPerson.getDisplayName();
                String[] separated = CurrentString.split(" ");
                String fname = separated[0];
                String lnam = separated[1];
                apps.setUser_SocialFirst(fname);
                apps.setUser_Sociallast(lnam);
                apps.setUser_Socialemail(email);
                //Log.e("email11", "" + email);
                //Log.e("st_response", "" + st_response);
                apps = new AppPrefs(LogInPage.this);
                apps.setUser_LoginWith("2");


                new send_LoginSocial_Data().execute();

              /*  Intent i = new Intent(LogInPage.this, Business_Registration.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/

                //txtName.setText(personName);
                //txtEmail.setText(email);

                // by default the profile url gives skip px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                /*personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE; */
                //  new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Globals.CustomToast(LogInPage.this, "Person information is null", getLayoutInflater());
              /*  Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();*/
            }
        } catch (Exception e) {
            Log.e("G Exception", e.getMessage());
            e.printStackTrace();
        }
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);

        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
      /*  TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(MainPage_drawer.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size()+"");*/

        img_cart.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInPage.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInPage.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInPage.this, Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    public void getFbKeyHash(String packageName) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                //System.out.println("YourKeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("YourKeyHash", "" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(LogInPage.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no = user_array_from_db.get(i).getPhone_no();
            String f_name = user_array_from_db.get(i).getF_name();
            String l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            String gender = user_array_from_db.get(i).getGender();
            String usertype = user_array_from_db.get(i).getUser_type();
            String login_with = user_array_from_db.get(i).getLogin_with();
            String str_rid = user_array_from_db.get(i).getStr_rid();
            String add1 = user_array_from_db.get(i).getAdd1();
            String add2 = user_array_from_db.get(i).getAdd2();
            String add3 = user_array_from_db.get(i).getAdd3();
            String landmark = user_array_from_db.get(i).getLandmark();
            String pincode = user_array_from_db.get(i).getPincode();
            String state_id = user_array_from_db.get(i).getState_id();
            String state_name = user_array_from_db.get(i).getState_name();
            String city_id = user_array_from_db.get(i).getCity_id();
            String city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(uid);
            contact.setUser_id(user_id);
            contact.setEmail_id(email_id);
            contact.setPhone_no(phone_no);
            contact.setF_name(f_name);
            contact.setL_name(l_name);
            contact.setPassword(password);
            contact.setGender(gender);
            contact.setUser_type(usertype);
            contact.setLogin_with(login_with);
            contact.setStr_rid(str_rid);
            contact.setAdd1(add1);
            contact.setAdd2(add2);
            contact.setAdd3(add3);
            contact.setLandmark(landmark);
            contact.setPincode(pincode);
            contact.setState_id(state_id);
            contact.setState_name(state_name);
            contact.setCity_id(city_id);
            contact.setCity_name(city_name);
            contact.setStr_response(str_response);
            user_data.add(contact);


        }
        db.close();
    }

    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            /*Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.clearDefaultAccountAndReconnect();*/
        }
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class send_Login_Data extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        LogInPage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("email_id", loginid));
                parameters.add(new BasicNameValuePair("password", password));
                parameters.add(new BasicNameValuePair("device_id", FirebaseInstanceId.getInstance().getToken()));


                //Log.e("2", "" + loginid);
                //Log.e("3", "" + password);
                //Log.e("4", "" + parameters);
                Log.e("5", "" + FirebaseInstanceId.getInstance().getToken());

                Globals.generateNoteOnSD(getApplicationContext(), parameters.toString());

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_Login", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            Globals.generateNoteOnSD(getApplicationContext(), result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(LogInPage.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(LogInPage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(LogInPage.this, "" + Message, getLayoutInflater());
                        //Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(LogInPage.this, "" + Message, getLayoutInflater());
                        // Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();
                        JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("User");

                        String user_id = jU.getString("id");
                        String email_id = jU.getString("email_id");
                        String phone_no = jU.getString("phone_no");
                        String f_name = jU.getString("first_name");
                        String l_name = jU.getString("last_name");
                        String passwod = jU.getString("password");
                        String gender = jU.getString("gender");
                        String user_type = jU.getString("role_id");
                        String login_with = jU.getString("login_with");
                        String str_rid = jU.getString("social_login_id");
                        String str_response = jU.getString("social_login_response");
                        String gstNo = jU.getString("gst_no");

                        Log.e("user", "" + user_id);

                        setRefershData();

                        if (user_data.size() == 0) {
                            db = new DatabaseHandler(LogInPage.this);
                            db.Add_Contact(new Bean_User_data(user_id, email_id, phone_no, f_name, l_name, password, gender, user_type, login_with, str_rid, "", "", "", "", "", "", "", "", "", str_response));
                            db.close();
                        }

                        apps = new AppPrefs(LogInPage.this);
                        apps.setUser_LoginWith("0");

                        //apps = new AppPrefs(LogInPage.this);
                        apps.setUser_LoginInfo("1");
                        apps.setUserRoleId(user_type);
                        apps.setUserId(user_id);
                        apps.setCsalesId(user_id);
                        apps.setDsalesId(user_id);
                        loadingView.dismiss();

                        if (apps.getUserRoleId().equals(C.COMP_SALES_PERSON)) {
                            Intent i = new Intent(LogInPage.this, UserTypeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        } else if (apps.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)) {
                            Intent i = new Intent(LogInPage.this, SalesTypeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        } else if (apps.getUserRoleId().equals(C.ADMIN)) {
                            Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else if (apps.getUserRoleId().equals(C.DISTRIBUTOR) || apps.getUserRoleId().equals(C.DIRECT_DEALER) || apps.getUserRoleId().equals(C.PROFESSIONAL) || apps.getUserRoleId().equals(C.THIRD_TIER_RETAILER) || apps.getUserRoleId().equals(C.DISTRIBUTOR_PROFESSIONAL)) {

                            if (gstNo.equalsIgnoreCase("0")) {
                                Intent intent = new Intent(getApplicationContext(), GSTFormActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        } else {

                            Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    }

                    //LogInPage.this.finish();
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class send_LoginSocial_Data extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        LogInPage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                //Log.e("11", "" + social_login_id);

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("social_login_id", social_login_id));

                Globals.generateNoteOnSD(getApplicationContext(), "User/App_CheckSocialLogin" + "\n" + parameters.toString());


                //Log.e("2", "" + social_login_id);

                //   //Log.e("4", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_CheckSocialLogin", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            Globals.generateNoteOnSD(getApplicationContext(), result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(LogInPage.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(LogInPage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //  Globals.CustomToast(LogInPage.this, "" + Message, getLayoutInflater());
                        //Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();
                        loadingView.dismiss();
                        // startActivity(new Intent(LogInPage.this, Business_Registration.class).putExtra("json", me.toString()));
                        Intent i = new Intent(LogInPage.this, Business_Registration.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(LogInPage.this, "" + Message, getLayoutInflater());

                        JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("User");

                        String user_id = jU.getString("id");
                        String email_id = jU.getString("email_id");
                        String phone_no = jU.getString("phone_no");
                        String f_name = jU.getString("first_name");
                        String l_name = jU.getString("last_name");
                        String passwod = jU.getString("password");
                        String gender = jU.getString("gender");
                        String user_type = jU.getString("role_id");
                        String login_with = jU.getString("login_with");
                        String str_rid = jU.getString("social_login_id");
                        String str_response = jU.getString("social_login_response");
                        // Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();
                        setRefershData();

                        if (user_data.size() == 0) {
                            db = new DatabaseHandler(LogInPage.this);
                            db.Add_Contact(new Bean_User_data(user_id, email_id, phone_no, f_name, l_name, passwod, gender, user_type, login_with, str_rid, "", "", "", "", "", "", "", "", "", str_response));
                            db.close();
                        }

                        apps = new AppPrefs(LogInPage.this);
                        apps.setUser_LoginInfo("1");
                        loadingView.dismiss();
                        Intent i = new Intent(LogInPage.this, MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }
}
