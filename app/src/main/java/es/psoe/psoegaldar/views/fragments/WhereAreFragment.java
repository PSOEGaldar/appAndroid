package es.psoe.psoegaldar.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.psoe.psoegaldar.R;


public class WhereAreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_where_are, container, false);
    }

    @Override
    public void onStart() {
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/INR____.TTF");
        super.onStart();

        TextView textView = (TextView) getActivity().findViewById(R.id.text_name_association);
        textView.setTypeface(face);
        textView = (TextView) getActivity().findViewById(R.id.text_association_address);
        textView.setTypeface(face);


        linkImageView(R.id.link_facebook, R.string.url_facebook);
        linkImageView(R.id.link_twitter, R.string.url_twitter);
        linkImageView(R.id.link_youtube, R.string.url_youtube);
        callPhoneNumber();
        sendEmail();
    }

    private void callPhoneNumber(){
        ImageView link = (ImageView) getActivity().findViewById(R.id.imageViewPhone);
        link.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("tel:" + getString(R.string.phone_number)));
                                        startActivity(intent);
                                    }
                                }
        );
    }

    private void sendEmail(){
        ImageView link = (ImageView) getActivity().findViewById(R.id.imageViewEmail);
        link.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                                "mailto",getString(R.string.email_address), null));
                                        startActivity(Intent.createChooser(intent, "Send Email..."));
                                    }
                                }
        );
    }

    private void linkImageView(Integer resIdButton, final Integer resIdLink){
        ImageView link = (ImageView) getActivity().findViewById(resIdButton);
        link.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(getString(resIdLink)));
                                        startActivity(intent);
                                    }
                                }
        );
    }
}