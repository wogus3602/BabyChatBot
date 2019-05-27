package com.jaiselrahman.dfchatbot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.headerfooter.songhang.library.SmartRecyclerAdapter;

import java.util.Random;
import java.util.zip.CheckedOutputStream;

public class FriendListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Random random = new Random(100);
    private RecyclerView.Adapter adapter;
    private CardView headerView, footerView;
    private String mParam1;
    private String mParam2;

    public FriendListFragment() {
    }

    public static Fragment newInstance() {

        return new FriendListFragment();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_friend, menu);
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_friend);
        initHeadAndFooterView();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initHeadAndFooterView() {
        //header view
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 400);
        layoutParams.setMargins(10, 10, 10, 10);

        headerView = new CardView(getContext());
        headerView.setLayoutParams(layoutParams);
        TextView head = new TextView(getContext());
        head.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        head.setTextColor(Color.BLACK);
        head.setGravity(Gravity.CENTER);
        //head.setText("추가 예정");
        head.setBackgroundColor(Color.WHITE);
        head.setBackgroundResource(R.drawable.head);
        headerView.addView(head);

        //footer view
        footerView = new CardView(getContext());
        footerView.setLayoutParams(layoutParams);
        TextView footer = new TextView(getContext());
        footer.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        footer.setText("추가 예정");
        footer.setGravity(Gravity.CENTER);
        footer.setTextColor(Color.BLACK);
        footer.setBackgroundColor(Color.WHITE);
        footerView.addView(footer);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend, container, false);

        RecyclerView recyclerView = (RecyclerView) view;

        adapter = new RecyclerView.Adapter<ViewHolder>() {

            boolean isStaggered;

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
                isStaggered = recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                CardView cardView = new CardView(getContext());
               // RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 300);
                RecyclerView.LayoutParams layoutParams1=new GridLayoutManager.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,350);
                layoutParams1.setMargins(10, 10, 10, 10);
                cardView.setLayoutParams(layoutParams1);


                TextView textView = new TextView(getContext() );
                textView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);

                cardView.addView(textView);



                ViewHolder viewHolder = new ViewHolder(cardView);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                int[] heights = {300, 400, 500};
                FrameLayout frameLayout = (FrameLayout) holder.itemView;
                if (isStaggered) {
                    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                    int height = heights[random.nextInt(3)];
                    lp.height = height;
                    holder.itemView.setLayoutParams(lp);
                    holder.itemView.setBackgroundColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                }
                if(position==0) {
                    ((TextView) frameLayout.getChildAt(0)).setText("친구 추가 예정");
                }else{
                    ((TextView) frameLayout.getChildAt(0)).setText("친구 추가 예정");
                }
                //((TextView) frameLayout.getChildAt(0)).setText("positon: " + position);
            }

            @Override
            public int getItemCount() {
                Log.d("asdasasdasd","1");
                return 9;
            }


        };

        Toast.makeText(getContext(),"GridLayout",Toast.LENGTH_SHORT);
        Log.d("asdasasdasd",""+getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter(adapter);
        smartRecyclerAdapter.setFooterView(footerView);
        smartRecyclerAdapter.setHeaderView(headerView);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(smartRecyclerAdapter);

        return view;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getPosition();
                    Context context = v.getContext();

                    if(p ==1){
                        Intent intent = new Intent(v.getContext() , ChatBotActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(context, "실험" + getPosition(), Toast.LENGTH_LONG).show();//각 아이템을 누르면 토스트 메세지가 뜨도록
                    }

                }
            });


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

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and mTextName
//
//        void onFriendItemSelected(User item, int type);
//    }
}

