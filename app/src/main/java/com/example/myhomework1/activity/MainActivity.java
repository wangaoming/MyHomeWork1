package com.example.myhomework1.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myhomework1.R;
import com.example.myhomework1.adapter.InsertAdapter;
import com.example.myhomework1.entity.Insert;
import com.example.myhomework1.service.InsertService;
import com.example.myhomework1.service.InsertServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private ListView listView;
private Button etAdd;
private Button etFix;
private Button etDel;

    private static final int ADD_REQUEST = 100;
    private static final int MODIFY_REQUEST = 101;
    private ListView insertList;
    private InsertAdapter insertAdapter;
    private InsertService insertService;
    private List<Insert> inserts;
    private int selectedPos;
    private Insert selectedInsert;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        listView = findViewById ( R.id.list );
        etAdd = findViewById ( R.id.add );
        etFix = findViewById ( R.id.fix );
        etDel = findViewById ( R.id.del );
        etAdd.setOnClickListener ( this );
        etFix.setOnClickListener ( this );
        etDel.setOnClickListener ( this );
        initData ( );
        // 初始化ListView
        insertList = findViewById ( R.id.list );
        insertAdapter = new InsertAdapter ( inserts );//获取数据源
        insertList.setAdapter ( insertAdapter );

        insertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                insertAdapter.setSelectItem(i);
                selectedPos = i;
                selectedInsert = (Insert) adapterView.getItemAtPosition(i);
                insertAdapter.notifyDataSetInvalidated();
            }
        });


    }

    private void initData() {

        insertService = new InsertServiceImpl (this);
        inserts = insertService.getAllInserts();

        // 若数据库中没数据，则初始化数据列表，防止ListView报错
        if(inserts == null) {
            inserts = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            // 更新列表
            selectedInsert = (Insert) bundle.get("insert");
            if (requestCode == MODIFY_REQUEST) {
                inserts.set(selectedPos, selectedInsert);
            } else if (requestCode == ADD_REQUEST) {
                inserts.add(selectedInsert);
            }
            // 刷新ListView
            insertAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivityForResult(intent,ADD_REQUEST);
                break;
            case R.id.fix:
                Intent intent1 = new Intent(MainActivity.this,Insert2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("update",selectedInsert);
                intent1.putExtras(bundle);
                startActivityForResult(intent1,MODIFY_REQUEST);
                break;
            case R.id.del:

                insertService.delete((selectedInsert.getName()));
                // 移除数据，并刷新adapter
                inserts.remove(selectedPos);
                insertAdapter.notifyDataSetChanged();


        }
    }
}
