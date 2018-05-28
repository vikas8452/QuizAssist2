package com.vikas.dell.quizassist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Starter extends Fragment {




    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;

    CameraSource cameraSource;

    final int REQUEST_CAMERA = 1001;
    public static final String AutoFocus = "AutoFocus";
    private AdView mAdView;
    @BindView(R.id.question)
    TextView question;
    public Starter() {
        // Required empty public constructor
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_CAMERA:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;

                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_starter, container, false);


        ButterKnife.bind(this,view);
        // button2.setVisibility(View.VISIBLE);


        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity()).build();
        MobileAds.initialize(getActivity(), "ca-app-pub-3515524233608559~8649260457");
        mAdView =view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (!textRecognizer.isOperational()) {
            Toast.makeText(getActivity(), "Dependencies Not Setup Correctly", Toast.LENGTH_SHORT).show();
        } else {
            //  Log.d("MAkjs","Enteredd in Else");
            cameraSource = new CameraSource.Builder(getActivity().getBaseContext(), textRecognizer).
                    setFacing(CameraSource.CAMERA_FACING_BACK).
                    setAutoFocusEnabled(true).
                    setRequestedPreviewSize(500, 800).
                    setRequestedFps(10.0f). build();


            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    if (ActivityCompat.checkSelfPermission(getActivity().getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(getActivity(),new String []{Manifest.permission.CAMERA},REQUEST_CAMERA);
                        return;
                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items=detections.getDetectedItems();
                    if(items.size()!=0)
                    {

                        question.post(new Runnable() {

                            @Override
                            public void run() {
                                StringBuilder string=new StringBuilder();
                                for(int i=0;i<items.size();++i)

                                {
                                    TextBlock textBlock=items.valueAt(i);

                                    string.append(textBlock.getValue());
                                    string.append("\n");
                                }

                                question.setText(string.toString());

                            }
                        });

                    }
                }
            });
        }


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://google.co.in/search?q="+question.getText();
                //   Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                //  startActivity(i);
                //  Intent j=new Intent (MainActivity.this,Main2Activity.class);
                //   j.putExtra("url",textView.getText());
                //   startActivity(j);

                com.vikas.dell.quizassist.Process fragment=new com.vikas.dell.quizassist.Process();
                Bundle bundle=new Bundle();
                bundle.putString("url", (String) question.getText());
                fragment.setArguments(bundle);

                loadFragment(fragment);
                //      button2.setVisibility(View.GONE);
            }
        });




        return view;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //transaction=getSupportFragmentManager().executePendingTransactions();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}
