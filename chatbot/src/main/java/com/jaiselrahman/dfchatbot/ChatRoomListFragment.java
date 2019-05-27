package com.jaiselrahman.dfchatbot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRoomListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView rcv;
    private LinearLayoutManager llm;
    private ChatAdapter wadapter;


    //private OnFragmentInteractionListener mListener;

    public ChatRoomListFragment() {
    }

    public static Fragment newInstance() {

        return new ChatRoomListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle(R.string.title_chat);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        rcv = (RecyclerView)view.findViewById(R.id.list);
        llm = new LinearLayoutManager(getContext());//종류는 총 3가지, ListView를 사용하기 위한 사용
        rcv.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        rcv.setLayoutManager(llm);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙힌다

        ArrayList<Itemlist> list = new ArrayList<>();//ItemFrom을 통해 받게되는 데이터를 어레이 리스트화 시킨다.

        //여기 코딩
        list.add(new Itemlist(R.drawable.baby_food,"마지막 대화","이유식"));


        wadapter = new ChatAdapter(list);//앞서 만든 리스트를 어뎁터에 적용시켜 객체를 만든다.
        rcv.setAdapter(wadapter);// 그리고 만든 겍체를 리싸이클러뷰에 적용시킨다.

        /*
        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "c",Toast.LENGTH_LONG).show();//각 아이템을 누르면 토스트 메세지가 뜨도록

            }
        });
        */

//        if(view instanceof RecyclerView){
//            RecyclerView recyclerView = (RecyclerView) view;
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            if(mAdapter == null){
//                mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for(DocumentSnapshot document: task.getResult()){
//                            HashMap<String, Boolean> users = (HashMap<String, Boolean>) document.get("users");
//                            if(users.size()==1){
//                                mUsers.put(MainActivity.USER_PROFILE.getUid(),  MainActivity.USER_PROFILE);
//                            }
//                            for(String uid: users.keySet()){
//                                if(!uid.equals(MainActivity.USER_PROFILE.getUid())){
//                                    Log.d("!!!!", "uid is :"+uid);
//                                    FirebaseFirestore.getInstance().collection("users")
//                                            .document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                                            User user = documentSnapshot.toObject(User.class);
//                                            mUsers.put(user.getUid(), user);
//                                        }
//                                    });
//                                }
//
//                            }
//                            Log.d("!!!!!", mUsers.size()+", "+ users.size());
//
//                        }
//
//                        mAdapter = new ChatRoomRecyclerViewAdapder(mQuery, mListener, mUsers);
//                        mAdapter.startListening();
//                        recyclerView.setAdapter(mAdapter);
//
//                    }
//                });
//
//            }else if(mUsers.get(mUser.getUid()) != null && mUsers.get(mUser.getUid()).getName() != MainActivity.USER_PROFILE.getName()){
//                Log.d("!!!!", "User Profile is different" + mUsers.get(mUser.getUid()).getName());
//                mUsers.put(mUser.getUid(), MainActivity.USER_PROFILE);
//                mAdapter = new ChatRoomRecyclerViewAdapder(mQuery, mListener, mUsers);
//                mAdapter.startListening();
//                recyclerView.setAdapter(mAdapter);
//
//            }
//            recyclerView.setAdapter(mAdapter);
//
//            final int initialTopPosition = recyclerView.getTop();
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    if(recyclerView.getChildAt(0).getTop() < initialTopPosition){
//
//                        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(5);
//                    }else{
//                        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
//
//
//                    }
//                }
//            });
//        }

        return view;
    }

// 필요한지 모르겠음
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
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
//        void onChatItemSelected(ChatRoom chatRoom, String name);
//    }
}
