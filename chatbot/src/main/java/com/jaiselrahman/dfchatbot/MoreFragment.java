package com.jaiselrahman.dfchatbot;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MoreFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView profile_img;//유저 이미지
    private TextView user_name;//유저 이름
    private TextView user_mail;//유저 메일
    private TextView notice;// 공지사항
    private TextView question;//문의사항
    private TextView app_info;//앱 정보
    private TextView mailcopy;

    private LinearLayout layout_info;
    private LinearLayout layout_notice;
    private LinearLayout layout_question;

    private Button layout_app_finish;
    private Button layout_notice_finish;
    private Button layout_question_finish;

    private Context context;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MoreFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance() {

        return new MoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle("더 보기");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_more, container, false);
        context=container.getContext();


        profile_img=view.findViewById(R.id.profile_img);//유저 이미지
        user_name=view.findViewById(R.id.user_name);//유저 이름
        user_mail=view.findViewById(R.id.user_mail);//유저 메일

        mailcopy=view.findViewById(R.id.mail);
        mailcopy.setOnClickListener(this::click);

        notice=view.findViewById(R.id.notice);// 공지사항
        question = view.findViewById(R.id.questions);//문의사항
        app_info=view.findViewById(R.id.app_infor);//앱 정보

        layout_notice=view.findViewById(R.id.layout_notice);//공지사항 레이아웃
        layout_question=view.findViewById(R.id.layout_questions);//문의사항 레이아웃
        layout_info=view.findViewById(R.id.layout_info);//앱 정보 레이아웃


        layout_app_finish=view.findViewById(R.id.layout_app_finish);//앱 정보 닫기 버튼
        layout_notice_finish=view.findViewById(R.id.layout_notice_finish);//공지사항 닫기 버튼
        layout_question_finish=view.findViewById(R.id.layout_question_finish);//문의사항 닫기 버튼


        notice.setOnClickListener(this::click);//공지사항 보이기
        question.setOnClickListener(this::click);//문의사항 보이기
        app_info.setOnClickListener(this::click);// 앱 정보 보이기

        layout_app_finish.setOnClickListener(this::click);// 앱정보 닫기 버튼
        layout_notice_finish.setOnClickListener(this::click);//공지사항 닫기 버튼
        layout_question_finish.setOnClickListener(this::click);//문의사항 닫기 버튼

        return view;


    }
    public void click(View view)
    {
        switch (view.getId()){
            case R.id.notice:// 공지사항 버튼
                notice_load();
                break;
            case R.id.questions://문의 사항 버튼
                question_load();
                break;
            case R.id.app_infor://앱 정보 보여주는 버튼
                app_info_load();
                //화면에 보여주는 코드
                break;
            case R.id.layout_app_finish://닫기 버튼
                layout_info.setVisibility(View.GONE);
                break;
            case R.id.layout_notice_finish://닫기 버튼
                layout_notice.setVisibility(View.GONE);
                break;
            case R.id.layout_question_finish:
                layout_question.setVisibility(View.GONE);
                break;
            case R.id.mail:
                sendEmail();
                //copy();
                break;
            }

    }
    /*
    public void copy()
    {
        ClipboardManager clipboard =(ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clip = (ClipData) ClipData.newPlainText("label"    ,"cstangga@naver.com");
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context,"메일 주소가 클립보드에 복사되었습니다",Toast.LENGTH_SHORT).show();

    }
    */
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"cstangga@naver.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMoreItemSelected(uri);
        }
    }
    public void question_load()//문의사항 txt문서 가져오기
    {
        try {

            // getResources().openRawResource()로 raw 폴더의 원본 파일을 가져온다.
            // txt 파일을 InpuStream에 넣는다. (open 한다)
            InputStream in1 = getResources().openRawResource(R.raw.question);

            if (in1 != null) {

                InputStreamReader stream1 = new InputStreamReader(in1, "UTF-8");
                BufferedReader buffer1 = new BufferedReader(stream1);

                String read;
                StringBuilder sb1 = new StringBuilder("");

                while ((read = buffer1.readLine()) != null) {
                    sb1.append("\n");
                    sb1.append(read);
                }

                in1.close();

                // id : read_info TextView를 불러와서
                //메모장에서 읽어온 문자열을 등록한다.
                TextView textView = view.findViewById(R.id.layout_question_text);
                textView.setText(sb1.toString());
                layout_question.setVisibility(View.VISIBLE);
                Toast.makeText(context, "파일을 읽어왓어요", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notice_load(){//공지사항 txt가져오기기
        try {

           // getResources().openRawResource()로 raw 폴더의 원본 파일을 가져온다.
            // txt 파일을 InpuStream에 넣는다. (open 한다)
            InputStream in = getResources().openRawResource(R.raw.notice);

            if (in != null) {

                InputStreamReader stream2 = new InputStreamReader(in, "UTF-8");
                BufferedReader buffer2 = new BufferedReader(stream2);

                String read;
                StringBuilder sb2 = new StringBuilder("");

                while ((read = buffer2.readLine()) != null) {
                    sb2.append("\n");
                    sb2.append(read);
                }

                in.close();

                // id : read_info TextView를 불러와서
                //메모장에서 읽어온 문자열을 등록한다.
                TextView textView = (TextView) view.findViewById(R.id.notice_text);
                textView.setText(sb2.toString());
                layout_notice.setVisibility(View.VISIBLE);
                Toast.makeText(context, "파일을 읽어왓어요", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //앱 정보 보여주는 메소드
    public void app_info_load() {
        try {

            // getResources().openRawResource()로 raw 폴더의 원본 파일을 가져온다.
            // txt 파일을 InpuStream에 넣는다. (open 한다)
            InputStream in3 = getResources().openRawResource(R.raw.app_info);

            if (in3 != null) {

                InputStreamReader stream3 = new InputStreamReader(in3, "UTF-8");
                BufferedReader buffer3 = new BufferedReader(stream3);

                String read;
                StringBuilder sb3 = new StringBuilder("");

                while ((read = buffer3.readLine()) != null) {
                    sb3.append("\n");
                    sb3.append(read);
                }

                in3.close();

                // id : read_info TextView를 불러와서
                //메모장에서 읽어온 문자열을 등록한다.
                TextView textView =  view.findViewById(R.id.app_info_text);
                textView.setText(sb3.toString());
                layout_info.setVisibility(View.VISIBLE);
                Toast.makeText(context, "파일을 읽어왓어요", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onMoreItemSelected(Uri uri);
    }
}
