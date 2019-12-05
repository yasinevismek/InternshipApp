package com.example.nazmi.mobilexstaj;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazmi.mobilexstaj.Common.Common;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.AboutBaseFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.ContactBaseFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.InternsFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.HomeBlogFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.WeatherTryFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.MyNotesFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.OurAppsBaseFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.UserEditFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.UseFulFragment;
import com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments.UserProfileFragment;
import com.example.nazmi.mobilexstaj.model.Sys;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Todo: Identify Navigation Drawer Tools
    Toolbar toolbar;
    DrawerLayout drawerLayout, drawerReturn;
    NavigationView navigationView;
    View headerLayout;
    ImageView userImage;
    //Todo: Identify Fragment Manager
    FragmentManager fragHome, fragUserProfile, fragFriends, fragUseful, fragFAQ, fragInternPie,
            fragMyNotes, fragAbout, fragApps, fragContact, fragProfileEdit, frag_messages;
    HomeBlogFragment home_frag;
    //Todo: Weather for Identify
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    //Todo: Identify FireBase Tools
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;


    //Todo: Declaration
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        //Todo: FireBase Authentication On Create
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        updateUI();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Nav User").child(firebaseAuth.getUid());


        //Todo: Toolbar On Create
        toolbar = findViewById(R.id.navAppBar_toolbar);
        setSupportActionBar(toolbar);

        //Todo: DrawerLayout On Create
        drawerLayout = findViewById(R.id.activityProfile_drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Todo: Navigation Drawer
        navigationView = findViewById(R.id.activityProfile_navView);
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout = navigationView.getHeaderView(0);
        //Todo: Nav Header Fill Text



        showHome();

        //Todo: Dexter with Permission
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            buildLocationCallBack();
                            buildLocationRequest();
                            if (ActivityCompat.checkSelfPermission(ProfileScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProfileScreenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ProfileScreenActivity.this);
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        Snackbar.make(coordinatorLayout, "Permission Denied", Snackbar.LENGTH_LONG).show();
                    }
                }).check();
    }

    //Todo: Current user null check
    private void updateUI() {
        if (firebaseUser != null) {
            Log.i("ProfileActivity", "firebaseUser != null");
        } else {
            Intent startIntent = new Intent(ProfileScreenActivity.this, MainActivity.class);
            startActivity(startIntent);
            Log.i("ProfileActivity", "firebaseUser == null");
        }
    }

    //Todo: Navigation Drawer Opened Start Fragment
    private void showHome() {
        home_frag = new HomeBlogFragment();
        FragmentManager home_fm = getSupportFragmentManager();
        home_fm.beginTransaction().replace(R.id.profile_main, home_frag).commit();
    }

    //Todo: Location Call
    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.current_location = locationResult.getLastLocation();
                //Log
                Log.d("Location", locationResult.getLastLocation().getLatitude() + "/" + locationResult.getLastLocation().getLongitude());
            }
        };
    }

    //Todo: Location Request
    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    //Todo : Back Pressed Action
    @Override
    public void onBackPressed() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activityProfile_drawerLayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.refreshDrawableState();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > (-1)) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }

    //Todo: Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile__screen_, menu);
        TextView emailll = headerLayout.findViewById(R.id.navHeader_tvUserEmail);
        TextView usernameee = headerLayout.findViewById(R.id.navHeader_tvUserName);
        String userName = firebaseAuth.getCurrentUser().getDisplayName();
        String email = firebaseAuth.getCurrentUser().getEmail();
        emailll.setText(email);
        usernameee.setText(userName);
        userImage = headerLayout.findViewById(R.id.navHeader_ivUser);
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseUser.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(userImage);
            }
        });

        return true;
    }

    //Todo: Item Action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile_settings) {
            fragProfileEdit = getSupportFragmentManager();
            fragProfileEdit.beginTransaction().replace(R.id.profile_main, new UserEditFragment()).addToBackStack(null).commit();
            return true;
        }
        if (id == R.id.log_out) {
            //firebaseAuth.signOut();
            //finish();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(ProfileScreenActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Todo: Validation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            fragHome = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragHome.beginTransaction().replace(R.id.profile_main, new HomeBlogFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_useraccount) {
            // Handle the user account action
            fragUserProfile = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragUserProfile.beginTransaction().replace(R.id.profile_main, new UserProfileFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_messages) {
            // Handle the user account action

            frag_messages = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            frag_messages.beginTransaction().replace(R.id.profile_main, new WeatherTryFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_friends) {
            // Handle the user account action
            fragFriends = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragFriends.beginTransaction().replace(R.id.profile_main, new InternsFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_usefullinks) {
            // Handle the user account action
            fragUseful = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragUseful.beginTransaction().replace(R.id.profile_main, new UseFulFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_mynotes) {
            // Handle the user account action
            fragMyNotes = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragMyNotes.beginTransaction().replace(R.id.profile_main, new MyNotesFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_logout) {
            // Handle the user account action
            //firebaseAuth.signOut();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileScreenActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //finish();
            //startActivity(new Intent(ProfileScreenActivity.this, MainActivity.class));
            //frag_logout = getSupportFragmentManager();
            //frag_logout.beginTransaction().replace(R.id.profile_main, new Logout_Fragment_Profile_Screen()).commit();
        } else if (id == R.id.nav_about) {
            // Handle the about account action
            fragAbout = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragAbout.beginTransaction().replace(R.id.profile_main, new AboutBaseFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_apps) {
            // Handle the user account action
            fragApps = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragApps.beginTransaction().replace(R.id.profile_main, new OurAppsBaseFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_contact) {
            // Handle the user account action
            //ContactBaseFragment contact_fragment_profile_screen=new ContactBaseFragment();
            fragContact = getSupportFragmentManager();
            getSupportFragmentManager().popBackStack();
            fragContact.beginTransaction().replace(R.id.profile_main, new ContactBaseFragment()).addToBackStack(null).commit();
            //fragContact.beginTransaction().replace(R.id.profile_main, contact_fragment_profile_screen).commit();
        }

        drawerReturn = findViewById(R.id.activityProfile_drawerLayout);
        drawerReturn.closeDrawer(GravityCompat.START);
        return true;
    }
}
