package com.gsered.gse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InviteActivity extends AppCompatActivity {

    private TextView moreoption,invitecode,whatsappshare;
    private ImageView image;
    SharedPreferences sharedPreferences;
    String username,uid,userngoname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Invite");

        moreoption = (TextView) findViewById(R.id.moreoption);
        invitecode = (TextView) findViewById(R.id.invitecode);
        whatsappshare = (TextView) findViewById(R.id.whatsappshare);
        image = (ImageView) findViewById(R.id.inviteimage);

        sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        username = sharedPreferences.getString("NAME","");
        uid = sharedPreferences.getString("USERID","");
        userngoname = sharedPreferences.getString("usersngo","");
        final String invitec = "MAP0F"+uid;

        invitecode.setText(invitec);

        final String sharetext = "I have recently used  \"GSE Red\", "+ userngoname +" Virtual Mall. It is very simple, eligant, useful and support my charity. GSE Red is the Internet's smart gateway for shopping, booking, news, games, cricket score, education, emails or even to see ZeeTV or your fav. channels. \n" +
                "\n" +
                "On the top of that,  use my referal code for signup \""+invitec+"\", Mapro donates INR 51 to help for eye operation with National Eyesight Foundation. \n" +
                "\n" +
                "Kindly check it out:\n" +
                "https://play.google.com/store/apps/details?id=com.gsered.gse&hl=en \n" +
                "\n" +
                "- "+username+"\n" +
                "Let's do Social Welfarization";

        whatsappshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, sharetext);
                startActivity(whatsappIntent);
            }
        });

        moreoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, sharetext);
                startActivity(sendIntent);
            }
        });
    }
}
