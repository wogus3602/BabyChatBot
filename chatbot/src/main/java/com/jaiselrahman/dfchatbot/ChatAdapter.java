package com.jaiselrahman.dfchatbot;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewholder> {

    private Activity activity;
    private ArrayList<Itemlist> datalist;

    //getItemCount, onCreateViewHolder, MyViewHolder, onBindViewholder 순으로 들어오게 된다.
    // 뷰홀더에서 초기세팅해주고 바인드뷰홀더에서 셋텍스트해주는 값이 최종적으로 화면에 출력되는 값

    @Override
    public ChatAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatlist, parent, false);//뷰 생성(아이템 레이아웃을 기반으로)
        MyViewholder viewholder1 = new MyViewholder(view);//아이템레이아웃을 기반으로 생성된 뷰를 뷰홀더에 인자로 넣어줌

        return viewholder1;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewholder holder, int position) {
        Itemlist data = datalist.get(position);//위치에 따라서 그에 맞는 데이터를 얻어오게 한다.
        holder.personalId.setText(data.getId());//앞서 뷰홀더에 세팅해준 것을 각 위치에 맞는 것들로 보여주게 하기 위해서 세팅해준다.
        holder.profile.setImageResource(data.getimageNum());
        holder.writtingTxt.setText(data.getTxt());

        /*
        //고친부분
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //Toast.makeText(context, "어떻게",Toast.LENGTH_LONG).show();//각 아이템을 누르면 토스트 메세지가 뜨도록
                Intent intent = new Intent(v.getContext() , ChatBotActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    //static 지움
    public class MyViewholder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView writtingTxt;
        TextView personalId;

        public MyViewholder(View itemview){
            super(itemview);

            profile = (ImageView) itemview.findViewById(R.id.image_profile);//item_layout.xml에 만든걸 세팅해준다.
            writtingTxt = (TextView) itemview.findViewById(R.id.text_name);
            personalId = (TextView) itemview.findViewById(R.id.text_latest_message);

        /*
            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    //Toast.makeText(context, "c",Toast.LENGTH_LONG).show();//각 아이템을 누르면 토스트 메세지가 뜨도록
                }
            });
        */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    //Toast.makeText(context, "어떻게",Toast.LENGTH_LONG).show();//각 아이템을 누르면 토스트 메세지가 뜨도록
                    Intent intent = new Intent(v.getContext() , ChatBotActivity.class);
                    v.getContext().startActivity(intent);
                }
            });


        }

    }
    //Activity activity
    public ChatAdapter(ArrayList<Itemlist> datalist){
        //this.activity = activity;//보여지는 액티비티
        this.datalist = datalist;//내가 처리하고자 하는 아이템들의 리스트
    }
}