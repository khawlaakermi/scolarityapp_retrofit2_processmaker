package com.example.fragmenttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmenttest.API.ProcessAPI;
import com.example.fragmenttest.Model.Case;
import com.example.fragmenttest.Model.ItemFormulaire;
import com.example.fragmenttest.Model.PostObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FormFragment extends Fragment {
    public static final String BASE_URL = "http://process.isiforge.tn/";
    private static Retrofit retrofit = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String id_dyn=getArguments().getString("id_dyn");
        String pro_uid=getArguments().getString("pro_uid");
        String tas_uid=getArguments().getString("tas_uid");
        //Retrieve token wherever necessary
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String token   = preferences.getString("TOKEN",null);
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        LinearLayout parentLayout = (LinearLayout) view.findViewById(R.id.contain_form);


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ProcessAPI processAPI = retrofit.create(ProcessAPI.class);
        Call<List<ItemFormulaire>> call = processAPI.champs("Bearer " + token,id_dyn);
        call.enqueue(new Callback<List<ItemFormulaire>>() {
            @Override
            public void onResponse(Call<List<ItemFormulaire>> call, Response<List<ItemFormulaire>> response) {
                List<ItemFormulaire> data = response.body();
                int i = 0;
                ItemFormulaire item;
                Map<String, View> tv = new HashMap<String, View>();

                int t = 0;
                while (i < data.size()) {
                    item = data.get(i);

                    if (item.getType() == null) {

                    } else {
                        if (item.getType().equals("text")) {
                            TextView title = new TextView(container.getContext());
                            title.setText(item.getLabel());
                            title.setGravity(2);
                            title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            parentLayout.addView(title);
                            EditText textView1 = new EditText(container.getContext());
                            textView1.setHint(item.getPlaceholder());
                            parentLayout.addView(textView1);
                            tv.put(item.getVariable(), textView1);
                        } else if (item.getType().equals("submit")) {
                            Button btn = new Button(container.getContext());
                            btn.setText(item.getLabel());
                            parentLayout.addView(btn);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    Map<String, String> m = new HashMap<String, String>();
                                    int k = 0;
                                    Set<String> keys = tv.keySet();
                                    for (String key : keys) {
                                        if (tv.get(key) instanceof EditText) {
                                            m.put(key, ((EditText) tv.get(key)).getText().toString());
                                        } else if (tv.get(key) instanceof RadioGroup) {
                                            RadioGroup r = (RadioGroup) tv.get(key);
                                            int selectedId = r.getCheckedRadioButtonId();
                                            if (selectedId != -1) {
                                                View ra = r.findViewById(selectedId);
                                                int radioId = r.indexOfChild(ra);
                                                RadioButton btn = (RadioButton) (r.getChildAt(radioId));
                                                String value = btn.getText().toString();
                                                m.put(key, value);
                                            }

                                        } else if (tv.get(key) instanceof Spinner) {
                                            Spinner s = (Spinner) tv.get(key);
                                            Object g = s.getSelectedItem();
                                            m.put(key, g.toString());

                                        } else {

                                        }

                                    }
                                    //while(k<parentLayout.getChildCount())
                                    PostObject form = new PostObject(pro_uid, tas_uid, m);
                                    Call<ResponseBody> post = processAPI.postRequest("Bearer " + token, form);
                                    post.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                            Toast.makeText(container.getContext(), "Votre Demande est envoyée avec succe", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            t.printStackTrace();
                                            Toast.makeText(container.getContext(), "fail ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }


                            });
                        } else {
                            if (item.getType().equals("title")) {
                                TextView title = new TextView(container.getContext());
                                title.setText(item.getLabel());
                                title.setPadding(10,10,10,10);
                                title.setTextColor(getResources().getColor(R.color.red));
                                title.setGravity(2);
                                parentLayout.addView(title);
                            } else {
                                if (item.getType().equals("dropdown")) {
                                    TextView title = new TextView(container.getContext());
                                    title.setText(item.getLabel());
                                    title.setGravity(2);
                                    title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                    parentLayout.addView(title);
                                    Spinner varSpinner = new Spinner(container.getContext());
                                    ArrayList<String> clubs = new ArrayList<String>();
                                    clubs.add(item.getPlaceholder());
                                    if (item.getSql().equals(""))
                                    {
                                    List<ItemFormulaire.option> option = item.getOptions();

                                    int j = 0;
                                    while (j < option.size()) {
                                        clubs.add(option.get(j).getValue());
                                        j++;
                                    }
                                    }
                                    else
                                    {
                                        Map<String, String> jsonParams = new HashMap<>();
                                        jsonParams.put("dyn_uid", id_dyn);
                                        jsonParams.put("field_id",item.getId());
                                        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
                                        //serviceCaller is the interface initialized with retrofit.create...
                                        Call<List<ItemFormulaire.option>> rep = processAPI.getenseignant("Bearer " + token,pro_uid,item.getId(),body);
                                        rep.enqueue(new Callback<List<ItemFormulaire.option>>() {
                                            @Override
                                            public void onResponse(Call<List<ItemFormulaire.option>> call, Response<List<ItemFormulaire.option>> response) {

                                                List<ItemFormulaire.option> enseignants=response.body();

                                                int j = 0;
                                                while (j < enseignants.size()) {
                                                    clubs.add(enseignants.get(j).getTexy());
                                                    j++;
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<List<ItemFormulaire.option>> call, Throwable t) {

                                            }
                                        });



                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, clubs);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    varSpinner.setAdapter(adapter);
                                    varSpinner.setSelection(0);
                                    tv.put(item.getVariable(), varSpinner);
                                    parentLayout.addView(varSpinner);

                                } else {
                                    if (item.getType().equals("radio")) {

                                        TextView title = new TextView(container.getContext());
                                        title.setText(item.getLabel());
                                        title.setGravity(2);
                                        title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                        parentLayout.addView(title);
                                        RadioGroup r = new RadioGroup(container.getContext());
                                        List<ItemFormulaire.option> option = item.getOptions();
                                        ArrayList<String> vedioproject = new ArrayList<String>();
                                        int j = 0;
                                        while (j < option.size()) {
                                            vedioproject.add(option.get(j).getValue());
                                            RadioButton b = new RadioButton(r.getContext());
                                            b.setText(option.get(j).getLabel());
                                            b.setId(j);
                                            r.addView(b);
                                            j++;
                                        }
                                        tv.put(item.getVariable(), r);
                                        parentLayout.addView(r);


                                    } else {

                                        if (item.getType().equals("datetime")) {
                                            TextView title = new TextView(container.getContext());
                                            title.setText(item.getLabel());
                                            title.setGravity(2);
                                            title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                            parentLayout.addView(title);
                                            EditText textView1 = new EditText(container.getContext());
                                            textView1.setHint(item.getFormat());
                                            parentLayout.addView(textView1);
                                            tv.put(item.getVariable(), textView1);
                                        }
                                        else if (item.getType().equals("subtitle"))
                                        {
                                            TextView title = new TextView(container.getContext());
                                            title.setText(item.getLabel());
                                            title.setGravity(2);
                                            title.setTextColor(getResources().getColor(R.color.red));
                                            parentLayout.addView(title);
                                        }
                                        else if (item.getType().equals("textarea"))
                                        {
                                            TextView title = new TextView(container.getContext());
                                            title.setText(item.getLabel());
                                            title.setGravity(2);
                                            title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                            parentLayout.addView(title);
                                            EditText textView1 = new EditText(container.getContext());
                                            textView1.setHint(item.getPlaceholder());
                                            textView1.setLines(4);
                                            textView1.setSingleLine(false);
                                            textView1.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                                            //textView1.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                                            parentLayout.addView(textView1);
                                            tv.put(item.getVariable(), textView1);
                                        }
                                        else if (item.getType().equals("grid")){
                                            GridView gridView = new GridView(container.getContext());
                                            gridView.setVerticalSpacing(3);
                                            gridView.setHorizontalSpacing(3);
                                            gridView.setLayoutParams(new GridView.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
                                            gridView.setNumColumns(3);


                                        }

                                    }
                                }
                            }
                        }
                    }
                    i = i + 1;
                }
            }

            @Override
            public void onFailure(Call<List<ItemFormulaire>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
        return view;
    }


}