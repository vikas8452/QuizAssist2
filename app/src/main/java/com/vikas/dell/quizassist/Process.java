package com.vikas.dell.quizassist;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Process extends Fragment {



    @BindView(R.id.btnId)
    Button btnId;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.webView1)
    WebView webView1;
    @BindView(R.id.webView2)
    WebView webView2;

    List<String> arr;
    int size;

    int optionA;
    int optionB;
    int optionC;
    String optiona;
    String optionb;
    String optionc;
    int DURATION=200;
    int counterArray[];
    int storeSpaces[];
    public Process() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_process, container, false);

        final WebView webView = (WebView) view.findViewById(R.id.webView);
        final WebView webView1 = (WebView) view.findViewById(R.id.webView1);
        final WebView webView2 = (WebView) view.findViewById(R.id.webView2);
        //  StringExtractor se = new StringExtractor("http://www.google.com");
        TextView contentView = (TextView) view.findViewById(R.id.contentView);

        String url="";
        Bundle args = getArguments();
         url = args.getString("url");
      //  Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();

     //   Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();
     //   Intent intent = getIntent();
       // String url = intent.getStringExtra("url");
        arr= Arrays.asList(url.split("\n"));
        ButterKnife.bind(this,view);
     //   disable.setEnabled(false);
        btnId.setEnabled(true);

        /* An instance of this class will be registered as a JavaScript interface */
        class MyJavaScriptInterface
        {
            private TextView contentView;

            public MyJavaScriptInterface()
            {
                Log.d("sdssdecd","Enter in Constructor");

            }


            @SuppressWarnings("unused")
            public void processContent(String aContent)
            {
                Log.d("dsdsd","Enteredd in the Process Content");
                final String content = aContent;
                contentView.post(new Runnable()
                {
                    public void run()
                    {
                        contentView.setText(content);
                    }
                });
            }
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "INTERFACE");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                // view.loadUrl("javascript:window.INTERFACE.processContent(document.getElementsByTagName('head')[0].innerText);");
                Log.d("jhdhdc","Entered int the On page Finished");
                webView.loadUrl("javascript:window.INTERFACE.processContent('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });


        webView1.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setJavaScriptEnabled(true);
       // webView3.getSettings().setJavaScriptEnabled(true);
        webView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  Log.i("shouldOverrideUrlLoading", url.toString());
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("onPageFinished", url.toString());
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i("onPageStarted", url.toString());
                super.onPageStarted(view, url, favicon);
            }



        });
        final String[] ur = new String[1];

        webView2.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  Log.i("shouldOverrideUrlLoading", url.toString());
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("onPageFinished", url.toString());
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i("onPageStarted", url.toString());
                ur[0] =url;
                super.onPageStarted(view, url, favicon);
            }



        });






        size=arr.size();
counterArray=new int[size];
storeSpaces=new int[size];
      



        for(int i=0;i<size;i++)
        {
            String s=arr.get(i);
            for(int j=0;j<s.length();j++)
            {
                if(s.charAt(j)==' ')
                {
                    storeSpaces[i]++;
                }
            }
           counterArray[i]= arr.get(i).length();


        }
        String newUrl="http://google.co.in/search?q=";
  
        for(int i=0;i<size;i++)
        {



                if (arr.get(i).contains("?") || counterArray[i] > 25||storeSpaces[i]>=3) {
                    newUrl += arr.get(i);
                }

        }
        webView.loadUrl(newUrl);

        webView1.loadUrl(newUrl);

        webView2.loadUrl(newUrl);



              for (int i = 0; i < size; i++) {

                  if (!(arr.get(i).contains("?") || counterArray[i] > 25||storeSpaces[i]>=3)) {
                      String arr1[] = arr.get(i).split(" ");

                      webView.findAllAsync(arr1[0]);
                      Log.d("dfdfdf", arr1[0]);
                      optiona = arr1[0];
                      arr.set(i, "#");

                      break;
                  }

          }





          webView.setFindListener(new WebView.FindListener() {
              @Override
              public void onFindResultReceived(int i, int i1, boolean b) {

                  if(i1>optionA)
                  optionA = i1;


                  showAnswer();

              }

          });
       
              for (int i = 0; i < size; i++) {

                  if (!(arr.get(i).contains("?") || counterArray[i] > 25||storeSpaces[i]>=3) && !arr.get(i).equals("#")) {
                      String arr1[] = arr.get(i).split(" ");


                      webView1.findAllAsync(arr1[0]);
                      //webView.findAllAsync("s");
                      Log.d("dff", arr1[0]);

                      optionb = arr1[0];


                      arr.set(i, "*");


                      break;
                  }
         

          }


          webView1.setFindListener(new WebView.FindListener() {
              @Override
              public void onFindResultReceived(int i, final int i1, boolean b) {
                 if(i1>optionB)
                    optionB=i1;

             showAnswer();


              }
          });




              for (int i = 0; i < size; i++) {

                  Log.d("d", "Enetred in for");


                  if (!(arr.get(i).contains("?") || counterArray[i] > 25||storeSpaces[i]>=3) && !arr.get(i).equals("*") && !arr.get(i).equals("#")) {
                      String arr1[] = arr.get(i).split(" ");


                      webView2.findAllAsync(arr1[0]);
                      optionc = arr1[0];
                      Log.d("df", arr1[0]);

                      arr.set(i, "");
                      break;
                  }

            
          }
          webView2.setFindListener(new WebView.FindListener() {
              @Override
              public void onFindResultReceived(int i, final int i1, boolean b) {

                  if (i1 > optionC)
                      optionC = i1;
showAnswer();
              }
          });

        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer();
             webView.findNext(true);
             webView1.findNext(true);
             webView2.findNext(true);
                Log.d("dfggg","Show Ans");

            }
        });

        return view;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        webView.onResume();
        webView1.onResume();
        webView2.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        webView.onPause();
        webView1.onPause();
        webView2.onPause();
    }

    public  void showAnswer()
    {
Log.d("dfss",optionA+"");
Log.d("dfssb",optionB+"");
Log.d("dfssa",optionC+"");
        if (optionA > optionB && optionA > optionC) {

            final Toast toast = Toast.makeText(getActivity(),    optionA + "    " + optiona, Toast.LENGTH_SHORT);
            button.setText(optionA + "    " + optiona);
            button1.setText(optionB + "    " + optionb);
            button2.setText(optionC + "    " + optionc);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, DURATION);
        } else if (optionB > optionA && optionB > optionC) {

            final Toast toast = Toast.makeText(getActivity(),  optionB + "    " + optionb, Toast.LENGTH_SHORT);
            button.setText(optionA + "    " + optiona);
            button1.setText(optionB + "    " + optionb);
            button2.setText(optionC + "    " + optionc);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, DURATION);
        } else if (optionC > optionA && optionC > optionB) {
            final Toast toast = Toast.makeText(getActivity(),  optionC + "   " + optionc, Toast.LENGTH_SHORT);
            // Log.d("ffdfdfd", "Entered in the webview 2");
            button.setText(optionA + "    " + optiona);
            button1.setText(optionB + "    " + optionb);
            button2.setText(optionC + "    " + optionc);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, DURATION);
        }


    }
    private void showAnswerForNot()
    {
        if ( optionA ==0) {

            final Toast toast= Toast.makeText(getActivity(), "option   A:  " + optionA + " "+optiona, Toast.LENGTH_SHORT);

            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, DURATION);
        }   else if ( optionB ==0 ) {
            final Toast toast=   Toast.makeText(getActivity(), "option  B:  " + optionB + "    "+optionb, Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, DURATION);
        }    else if ( optionC ==0) {
            final Toast toast=  Toast.makeText(getActivity(), "option    C:  " + optionC + "   "+optionc, Toast.LENGTH_SHORT);

            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, DURATION);
        }
    }

   
}
