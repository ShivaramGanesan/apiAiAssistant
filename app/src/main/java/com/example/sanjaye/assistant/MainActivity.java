package com.example.sanjaye.assistant;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {

    AIService aiService;
    AIDataService aiDataService;
    AIRequest aiRequest;
    List<ChatMessage> chatMessageList=new ArrayList<>();
    RecyclerView recyclerView;
    //CustomAdapter mCustomAdapter;
    Adapt adapt;
    Button sendButton;
    EditText sendEdittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AIConfiguration aiConfiguration = new AIConfiguration("a3481f61aed64e82a907e9339be3dd2c",AIConfiguration.SupportedLanguages.English, AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, aiConfiguration);
        aiService.setListener(this);

        aiDataService = new AIDataService(aiConfiguration);
        aiRequest = new AIRequest();





        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

       // mCustomAdapter = new CustomAdapter(chatMessageList);
        adapt = new Adapt(chatMessageList);
        
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(mCustomAdapter);
        recyclerView.setAdapter(adapt);

        sendEdittext = (EditText)findViewById(R.id.edittext);
        sendButton=(Button)findViewById(R.id.button);



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                String chat = sendEdittext.getText().toString();
                addChat(chat);
                aiRequest.setQuery(chat);
                sendEdittext.setText("");
               // getReply("hello");
            }
        });
    }

    private void getReply(String reply) {

        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        ChatMessage chatMessage = new ChatMessage(reply,hour+":"+min,false);
        chatMessageList.add(chatMessage);

       // mCustomAdapter.notifyDataSetChanged();
        adapt.notifyDataSetChanged();


    }

    private void addChat(String s) {
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        ChatMessage chatMessage = new ChatMessage(s,hour+":"+min,true);
        chatMessageList.add(chatMessage);

        String fileName = "queries_chat_bot";
        FileOutputStream outputStream = null;
        try{
            outputStream = openFileOutput(fileName,Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

       // mCustomAdapter.notifyDataSetChanged();

        adapt.notifyDataSetChanged();

        new AsyncTask<AIRequest, Void, AIResponse>(){
            @Override
            protected AIResponse doInBackground(AIRequest... aiRequests) {
                final AIRequest request = aiRequests[0];
                try{
                    final AIResponse response = aiDataService.request(aiRequest);
                    return response;
                }catch (AIServiceException e){

                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse response) {
                if(response!=null){
                    Result result = response.getResult();
                    Toast.makeText(MainActivity.this, ""+response.getResult().getAction().toString(), Toast.LENGTH_SHORT).show();
                    String reply = result.getFulfillment().getSpeech().toString();
                    getReply(reply);

                }
            }
        }.execute(aiRequest);

        recyclerView.smoothScrollToPosition(chatMessageList.size());
    }


    @Override
    public void onResult(AIResponse response) {

        Result result = response.getResult();
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }
        getReply("Query :"+result.getResolvedQuery()+"\nAction: "+result.getAction()+"\nparameters: "+parameterString);


    }

    @Override
    public void onError(AIError error) {
        getReply(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
