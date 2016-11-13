package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.UserDto;
import com.sonda.emsysmobile.managers.MultimediaManager;
import com.sonda.emsysmobile.ui.attachgeoloc.AttachGeoLocView;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.ImageGalleryPresenter;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.interfaces.ProgressBarListener;
import com.sonda.emsysmobile.ui.views.dialogs.AttachDescriptionDialogFragment;
import com.sonda.emsysmobile.ui.views.dialogs.AttachImageDialogFragment;
import com.sonda.emsysmobile.utils.DateUtils;
import com.sonda.emsysmobile.utils.UIUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsView extends AppCompatActivity implements
        OnListFragmentInteractionListener,
        AttachImageDialogFragment.OnAttachImageDialogListener,
        AttachDescriptionDialogFragment.OnAttachDescriptionDialogListener, 
        View.OnClickListener,
        ProgressBarListener {

    public static final int SHOULD_UPDATE_MAP = 1;
    static final int PICK_IMAGE_REQUEST = 2;
    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final String TAG = EventDetailsView.class.getName();

    private String mCurrentPhotoPath;

    private EventDto mEvent;

    private TextView mGeneralInformationTitleEventIdentifier;
    private TextView mInformantName;
    private TextView mInformantPhone;
    private TextView mCreatedDate;
    private TextView mStatus;
    private TextView mStreet;
    private TextView mNumber;
    private TextView mCorner;
    private TextView mSector;
    private TextView mCategory;
    private TextView mOrigin;
    private TextView mType;
    private TextView mGeneralDescription;
    private ImageButton mImagesButton;
    private ImageButton mVideosButton;
    private ImageButton mAudioButton;
    private ProgressBar mProgressBar;

    private FloatingActionMenu mFloatingActionMenu;
    private FloatingActionButton mUpdateDescriptionBtn;
    private FloatingActionButton mAttachGeolocationBtn;
    private FloatingActionButton mAttachImageBtn;
    private FloatingActionButton mReportTimeBtn;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Se agrega la flecha de ir hacia atras.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mGeneralInformationTitleEventIdentifier = (TextView) findViewById(R.id.general_information_title_event_identifier);

        mInformantName = (TextView) findViewById(R.id.informant_name);
        mInformantPhone = (TextView) findViewById(R.id.informant_phone);

        mCreatedDate = (TextView) findViewById(R.id.event_date_created);
        mStatus = (TextView) findViewById(R.id.event_status);

        mCategory = (TextView) findViewById(R.id.category);
        mType = (TextView) findViewById(R.id.type);
        mOrigin = (TextView) findViewById(R.id.origin);

        mGeneralDescription = (TextView) findViewById(R.id.general_description);

        mStreet = (TextView) findViewById(R.id.informant_street);
        mCorner = (TextView) findViewById(R.id.informant_corner);
        mNumber = (TextView) findViewById(R.id.informant_number);
        mSector = (TextView) findViewById(R.id.informant_sector);



        mImagesButton = (ImageButton) findViewById(R.id.button_images);
        mImagesButton.setOnClickListener(this);
        mVideosButton = (ImageButton) findViewById(R.id.button_video);
        mVideosButton.setOnClickListener(this);
        mAudioButton = (ImageButton) findViewById(R.id.button_audio);
        mAudioButton.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);

        mUpdateDescriptionBtn = (FloatingActionButton) findViewById(R.id.button_update_description);
        mUpdateDescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDescriptionDialog();
            }
        });

        mAttachGeolocationBtn = (FloatingActionButton) findViewById(R.id.button_attach_geolocation);
        mAttachGeolocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAttachGeolocationView();
            }
        });

        mAttachImageBtn = (FloatingActionButton) findViewById(R.id.button_attach_image);
        mAttachImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAttachImageView();
            }
        });

        mReportTimeBtn = (FloatingActionButton) findViewById(R.id.button_report_time);
        UserDto userDto = GlobalVariables.getUserData();
        if (userDto != null){
            // If user is not logged as resource
            if (userDto.getRoles().getResources().size() == 0){
                mReportTimeBtn.setVisibility(View.GONE);
            }
        }

        mReportTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportTime();
            }
        });

        updateViewData((EventDto) getIntent().getSerializableExtra("EventDto"));

        // Inicializacion de fragment de mapa.
        EventDetailsPresenter.initMapFragment(EventDetailsView.this, mEvent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se maneja la flecha de ir hacia atras.
        if (item.getItemId() == android.R.id.home) {
            // Cierra la Activity y vuelve a la Activity anterior (si la hubo).
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public final void updateViewData(EventDto event) {
        mEvent = event;
        if (mEvent != null) {
            String eventIdentifier = mEvent.getIdentifier() + " - " + mEvent.getExtensions().get(0).getZone().getName();
            mGeneralInformationTitleEventIdentifier.setText(eventIdentifier);

            if ((mEvent.getInformant() != null) && (!mEvent.getInformant().equals(""))) {
                mInformantName.setText(mEvent.getInformant());
            }

            if ((mEvent.getPhone() != null) && (!mEvent.getPhone().equals(""))) {
                mInformantPhone.setText(mEvent.getPhone());
            }

            if (mEvent.getCreatedDate() != null) {
                mCreatedDate.setText(DateUtils.dateToString(mEvent.getCreatedDate()));
            }

            if ((mEvent.getGeneralDescription() != null ) && (mEvent.getGeneralDescription() != "")){
                mGeneralDescription.setText(mEvent.getGeneralDescription());
            }

            if ((mEvent.getStatus() != null) && (!mEvent.getStatus().equals(""))) {
                mStatus.setText(mEvent.getStatus());
            }

            if ((mEvent.getStreet() != null) && (!mEvent.getStreet().equals(""))) {
                mStreet.setText(mEvent.getStreet());
            }
            if ((mEvent.getNumber() != null) && (!mEvent.getNumber().equals(""))) {
                mNumber.setText(mEvent.getNumber());
            }
            if (mEvent.getCorner() != null && !mEvent.getCorner().equals("")) {
                mCorner.setText(mEvent.getCorner());
            }

            if ((mEvent.getCategory() != null) && (!mEvent.getCategory().getKey().equals(""))) {
                mCategory.setText(mEvent.getCategory().getKey());
            }

            if ((mEvent.getSectorCode() != null) && (!mEvent.getSectorCode().equals(""))) {
                mSector.setText(mEvent.getSectorCode());
            }

            mType.setText("Aplicación");
            if ((mEvent.getOrigin() != null) && (!mEvent.getOrigin().equals(""))) {
                mOrigin.setText(mEvent.getOrigin());
            }

            EventDetailExtensionsFragment extensionsFragment =
                    EventDetailExtensionsFragment.newInstance(mEvent);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, extensionsFragment).commit();
        }
    }

    public void showMap() {
        findViewById(R.id.map_container).setVisibility(View.VISIBLE);
        EventDetailMapView mapFragment = EventDetailMapView.getInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                mapFragment, EventDetailMapView.class.getSimpleName()).commit();
    }

    private void showUpdateDescriptionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AttachDescriptionDialogFragment attachDescriptionDialogFragment =
                AttachDescriptionDialogFragment.newInstance();
        attachDescriptionDialogFragment.show(fm, "fragment_update_description");
    }

    private void goToAttachGeolocationView() {
        if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
            int extensionID = mEvent.getExtensions().get(0).getIdentifier();
            Intent intent = new Intent(EventDetailsView.this, AttachGeoLocView.class);
            Bundle extras = new Bundle();
            extras.putInt("ExtensionId", extensionID);
            intent.putExtras(extras);
            EventDetailsPresenter.showGeolocationAttachView(intent);
        }
    }

    private void goToAttachImageView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AttachImageDialogFragment attachImageDialogFragment =
                AttachImageDialogFragment.newInstance();
        attachImageDialogFragment.show(fragmentManager, "fragment_attach_image_menu");
    }

    @Override
    public void onOpenGalleryButtonSelected() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onOpenCameraButtonSelected() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                UIUtils.showToast(this, getString(R.string.error_access_camera_message));
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.sonda.emsysmobile.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void reportTime(){
        if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
            int extensionID = mEvent.getExtensions().get(0).getIdentifier();
            EventDetailsPresenter.reportTime(this, extensionID);
        }
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == SHOULD_UPDATE_MAP)) {
            Log.d(TAG, "Updating map...");
            EventDetailsPresenter.updateMapFragment();
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mAttachImageBtn.setShowProgressBackground(true);
            mAttachImageBtn.setIndeterminate(true);
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
                    int extensionID = mEvent.getExtensions().get(0).getIdentifier();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
                    String fileName = "IMG_EM_" + timeStamp + ".jpg";
                    Log.d(TAG, "Uploading file: " + fileName);
                    MultimediaManager manager = MultimediaManager.getInstance(this);
                    manager.uploadImage(extensionID, fileName, bitmap, new ApiCallback() {
                        @Override
                        public void onSuccess(Object object) {
                            mAttachImageBtn.hideProgress();
                            UIUtils.showToast(EventDetailsView.this,
                                    getString(R.string.message_image_attached));
                        }

                        @Override
                        public void onLogicError(String errorMessage, int errorCode) {
                            mAttachImageBtn.hideProgress();
                            UIUtils.showToast(EventDetailsView.this,
                                    getString(R.string.error_message_image_not_attached));
                        }

                        @Override
                        public void onNetworkError(VolleyError error) {
                            mAttachImageBtn.hideProgress();
                            UIUtils.showToast(EventDetailsView.this,
                                    getString(R.string.error_message_image_not_attached));
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mAttachImageBtn.setShowProgressBackground(true);
            mAttachImageBtn.setIndeterminate(true);
            File imgFile = new File(mCurrentPhotoPath);
            if(imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
                    int extensionID = mEvent.getExtensions().get(0).getIdentifier();
                    MultimediaManager multimediaManager = MultimediaManager.getInstance(this);
                    String fileName = imgFile.getName();
                    multimediaManager.uploadImage(extensionID, fileName, bitmap, new ApiCallback() {
                        @Override
                        public void onSuccess(Object object) {
                            mAttachImageBtn.hideProgress();
                            UIUtils.showToast(EventDetailsView.this,
                                    getString(R.string.message_image_attached));
                        }

                        @Override
                        public void onLogicError(String errorMessage, int errorCode) {
                            mAttachImageBtn.hideProgress();
                            UIUtils.showToast(EventDetailsView.this,
                                    getString(R.string.error_message_image_not_attached));
                        }

                        @Override
                        public void onNetworkError(VolleyError error) {
                            mAttachImageBtn.hideProgress();
                            UIUtils.showToast(EventDetailsView.this,
                                    getString(R.string.error_message_image_not_attached));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto event) {
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public final void onAttachDescription(String descriptionText) {
        UIUtils.hideSoftKeyboard(this);
        if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
            int extensionID = mEvent.getExtensions().get(0).getIdentifier();
            EventDetailsPresenter.attachDescriptionForExtension(this, descriptionText, extensionID);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button_images) {
            Log.d(TAG, "Botón de imágenes pulsado");
            ImageGalleryPresenter
                    .loadGallery(EventDetailsView.this, mEvent.getImageDescriptions());
        } else if (view.getId() == R.id.button_video) {
            Log.d(TAG, "Botón de video pulsado");
        } else if (view.getId() == R.id.button_audio) {
            Log.d(TAG, "Botón de audio pulsado");
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "IMG_EM_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
