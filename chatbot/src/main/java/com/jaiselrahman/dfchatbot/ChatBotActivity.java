package com.jaiselrahman.dfchatbot;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.cloud.TransportOptions;
import com.jaiselrahman.dfchatbot.adapter.ChatsAdapter;
import com.jaiselrahman.dfchatbot.model.Cards;
import com.jaiselrahman.dfchatbot.model.Message;
import com.jaiselrahman.dfchatbot.model.MessageType;
import com.jaiselrahman.dfchatbot.model.Quick;
import com.jaiselrahman.dfchatbot.model.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.Vector;


public class ChatBotActivity extends AppCompatActivity {

    ///
    ////////
    private static final String TAG = ChatBotActivity.class.getSimpleName();
    private Vector<Message> chatMessages = new Vector<>();
    private ChatsAdapter chatsAdapter;
    private RecyclerView chatList;
    private TextView messageText;//텍스트 입력창
    private ImageView send;//보내기 버튼튼
   private Message currentMessage;
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private UUID uuid = UUID.randomUUID();

    //보이스 관련 코드
    private static TextToSpeech assistant_voice=null;
    private ImageView voiceBtn;// 보이스 입력 버튼
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (getIntent().getAction() != null && getIntent().getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
            query = getIntent().getStringExtra(SearchManager.QUERY);
            Log.e("Query:",query);   //query is the search word
        }


        //여기는 json파일 연결하는 코드
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(getResources().openRawResource(R.raw.lee));
            String projectID = ((ServiceAccountCredentials) credentials).getProjectId();
            sessionsClient = SessionsClient.create(SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build());
            sessionName = SessionName.of(projectID, uuid.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendWelcomeEvent();

        final FloatingActionButton fab = findViewById(R.id.move_to_down);//스크롤 올리면 맨 아래로 이동하는 버튼
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatList.scrollToPosition(chatsAdapter.getItemCount() - 1);
            }
        });

        chatsAdapter = new ChatsAdapter(this, chatMessages);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        chatList = findViewById(R.id.chat_list_view);
        chatList.setLayoutManager(linearLayoutManager);
        chatList.setAdapter(chatsAdapter);
        chatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (position != RecyclerView.NO_POSITION && position >= chatsAdapter.getItemCount() - 4) {
                    fab.hide();
                } else if (fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        messageText = findViewById(R.id.chat_edit_text1);
        send = findViewById(R.id.enter_chat1);
        send.setOnClickListener(this::sendMessageBtn);//메시지 보내기

        //보이스 입력 부분
        initvoice();// 마이크 초기화
        voiceBtn=findViewById(R.id.voicebtn);
        voiceBtn.setOnClickListener(this::voicesend);//보이스 입력
    }


    private void initvoice() {
        assistant_voice=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR)
                    assistant_voice.setLanguage(Locale.KOREA);
            }
        });
    }

    private void voicesend(View view) {
        //여기는 음성을 입력하고, 다 입력이 되면 아래 onActivityResult로 감
        android.content.Intent intent=new android.content.Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"말씀 해주세요");

        try{
            startActivityForResult(intent,2);
        }catch (ActivityNotFoundException e)
        {
            Toast.makeText(this,"다시 말씀 해주세요",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable android.content.Intent data) {
        //바로 위 voicesend 메서드에서 받아옴
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK)
        {
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            messageText.setText(Editable.Factory.getInstance().newEditable(result.get(0)));
            sendMessageBtn(send);

        }
    }

    protected void sendMessageBtn(View view){
        if (TextUtils.isEmpty(messageText.getText().toString().trim())) {
            Toast.makeText(this,"메시지를 입력해주세요",Toast.LENGTH_SHORT).show();
        }
        final Message message = new Message();
        message.setText(messageText.getText().toString());//텍스트 입력 창
        if(message.getText().equals(""))
        {
            Toast.makeText(this,"메시지를 입력해주세요",Toast.LENGTH_SHORT).show();
        }
        else {
            message.setStatus(Status.WAIT);
            message.setTimeStamp(new Date().getTime());
            message.setMessageType(MessageType.MINE);
            chatMessages.add(message);
            currentMessage = message;
            sendMessage(message.getText());
            chatsAdapter.notifyDataSetChanged();
            messageText.setText("");
            chatList.smoothScrollToPosition(chatsAdapter.getItemCount());
        }

    }

    void sendWelcomeEvent() {
        new RequestTask(this).execute();
    }

    private void sendMessage(String message) {
        new RequestTask(this).execute(message);
    }


    static class RequestTask extends AsyncTask<String, Void, DetectIntentResponse> {

        private WeakReference<ChatBotActivity> activity;
        private SessionsClient sessionsClient;

        RequestTask(ChatBotActivity activity) {
            this.activity = new WeakReference<>(activity);
            this.sessionsClient = activity.sessionsClient;
        }

        @Override
        protected DetectIntentResponse doInBackground(String... requests) {
            try {
                return sessionsClient.detectIntent(activity.get().sessionName,
                        QueryInput.newBuilder()
                                .setText(TextInput.newBuilder()
                                        .setText(requests[0])
                                        .setLanguageCode("ko")
                                        .build())
                                .build());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public WeakReference<ChatBotActivity> getActivity() {
            return activity;
        }

        public RequestTask() {
            super();
        }

        @Override
        //답변 메서드
        protected void onPostExecute(DetectIntentResponse response) {
            if (response != null) {//다이얼로그플로우에서 답변이 있으면
                if (activity.get().currentMessage != null) {
                    activity.get().currentMessage.setStatus(com.jaiselrahman.dfchatbot.model.Status.SENT);
                }
                ArrayList<Quick> quickrepliesses = null;
                Message QuickRepliesMessage = null;
                ArrayList<Cards> cards = null;
                Message cardMessage = null;

                List<Intent.Message> messages = response.getQueryResult().getFulfillmentMessagesList();
                for (Intent.Message m : messages) {

                    if (m.hasPayload()) {
                        boolean isEventsLists = m.getPayload().getFieldsMap().containsKey("EVENT_LISTS");
                        if (isEventsLists)
                            isEventsLists = m.getPayload().getFieldsMap().get("EVENT_LISTS").getBoolValue();
                        if (isEventsLists) {
                            new RequestTask(activity.get()).execute("technical events");
                            new RequestTask(activity.get()).execute("non technical events");
                            new RequestTask(activity.get()).execute("online events");
                            return;
                        }
                    } else if (m.hasCard()) {
                        super.onPostExecute(response);

                        if (cards == null) {

                            cards = new ArrayList<>();
                            cardMessage = new Message();
                            cardMessage.setTimeStamp(new Date().getTime());
                            cardMessage.setMessageType(MessageType.OTHER_CARDS);
                        }
                        Cards card = new Cards();
                        card.setTitle(m.getCard().getTitle());
                        card.setSubtitle(m.getCard().getSubtitle());
                        card.setImgUrl(m.getCard().getImageUri());
                        card.setButtons(m.getCard().getButtons(0));
                        cards.add(card);
                    } else if (m.hasText()) {//단순 텍스트 답변
                        Message msg = new Message();
                        msg.setTimeStamp(new Date().getTime());
                        msg.setMessageType(MessageType.OTHER);
                        msg.setText(m.getText().getText(0));
                        addMessage(msg);
                        //텍스트 메시지를 읽어줌
                        assistant_voice.speak(msg.toString(),TextToSpeech.QUEUE_FLUSH,null);

                    }else if (m.hasQuickReplies()){
                        super.onPostExecute(response);
                        if (quickrepliesses == null) {

                            quickrepliesses = new ArrayList<>();
                            QuickRepliesMessage = new Message();
                            QuickRepliesMessage.setMessageType(MessageType.QuickReplies);
                        }
                        Quick quickreplies = new Quick();
                        quickreplies.setTitle1(m.getQuickReplies().getQuickReplies(0));
                        quickreplies.setTitle2(m.getQuickReplies().getQuickReplies(1));
                        quickrepliesses.add(quickreplies);

                        Log.d("1111111111111111111",""+m.getQuickReplies().getQuickReplies(0));
                    }
                }
                if (cardMessage != null) {
                    cardMessage.setCards(cards);
                    addMessage(cardMessage);
                }
                if(quickrepliesses !=null){

                    QuickRepliesMessage.setQuicks(quickrepliesses);

                    addMessage(QuickRepliesMessage);
                }
            } else {
                //답변이 없으면에러 인데 들어오면 바로 메시지를 보냈다고 실행이됨
                //Toast.makeText(activity.get(), "Oops! Something went wrong.\nPlease Check your Network.", Toast.LENGTH_SHORT).show();
                Toast.makeText(activity.get(), "시작", Toast.LENGTH_SHORT).show();
            }
        }

        void addMessage(Message message) {
            activity.get().currentMessage.setStatus(com.jaiselrahman.dfchatbot.model.Status.SENT);
            activity.get().chatMessages.add(message);
            activity.get().chatsAdapter.notifyDataSetChanged();
            activity.get().chatList.smoothScrollToPosition(activity.get().chatsAdapter.getItemCount());
        }
    }

}
