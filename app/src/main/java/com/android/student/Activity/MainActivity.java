package com.android.student.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.android.student.Adapter.ViewPagerAdapter;
import com.android.student.StuFragment.FirstFragment;
import com.android.student.ManFragment.ManagerFirstFragment;
import com.android.student.ManFragment.ManagerSecondFragment;
import com.android.student.ManFragment.ManagerThirdFragment;
import com.android.student.StuFragment.SecondFragment;
import com.android.student.StuFragment.ThirdFragment;
import com.android.student.DataBase.MyDatabaseHelper;
import com.android.student.App.LoginActivity;
import com.android.student.R;
/**
 * 主界面，包含三个Fragment,以学生身份和管理员身份登入系统时对应界面不同
 */
public class MainActivity extends AppCompatActivity {

    //dbHelper
    private MyDatabaseHelper dbHelper;
    private static int studentId;
    BottomNavigationView bottomNavigationView;
    //This is our viewPager
    private ViewPager viewPager;

    private ViewPagerAdapter adapter;
    //Fragments
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    ManagerFirstFragment managerFirstFragment;
    ManagerSecondFragment managerSecondFragment;
    ManagerThirdFragment managerThirdFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Intent intent = getIntent();
        studentId = intent.getIntExtra("id",-1);
        //创建或打开数据库
        dbHelper = new MyDatabaseHelper(this);
        dbHelper.getWritableDatabase();
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_first:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_second:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_third:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.switch_item:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quit_item:
                finish();
                break;
            default:
        }
        return true;
    }

    public static int  getStudentId(){
        return studentId;
    }
    public  Context  context(){return this;}
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(getStudentId()==0){
            managerFirstFragment=new ManagerFirstFragment();
            managerSecondFragment=new ManagerSecondFragment();
            managerThirdFragment=new ManagerThirdFragment();
            adapter.addFragment(managerFirstFragment);
            adapter.addFragment(managerSecondFragment);
            adapter.addFragment(managerThirdFragment);
        }else {
            firstFragment = new FirstFragment();
            secondFragment = new SecondFragment();
            thirdFragment = new ThirdFragment();
            adapter.addFragment(firstFragment);
            adapter.addFragment(secondFragment);
            adapter.addFragment(thirdFragment);
        }
        viewPager.setAdapter(adapter);
    }
}