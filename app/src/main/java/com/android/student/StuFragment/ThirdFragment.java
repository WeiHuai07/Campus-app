package com.android.student.StuFragment;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.student.Adapter.StudentAdapter;
import com.android.student.Dao.StudentDao;
import com.android.student.Entity.Student_info;
import com.android.student.Activity.MainActivity;
import com.android.student.App.MyApplication;
import com.android.student.R;
import com.android.student.Activity.StudentChangeActivity;
import com.android.student.Activity.ActActivity;
import com.android.student.Activity.YyActivity;
import java.util.List;

/**
 * 底部导航栏对应的第三个Fragment
 * 显示个人信息
 * 从修改信息的界面返回ThirdFragment时，状态为onResume,重写onResume方法实现刷新数据。
 */
public class ThirdFragment extends Fragment {
    Context context = MyApplication.getInstance();
    private StudentDao student = new StudentDao(context);
    private List<Student_info> StudentList = student.getStudent(MainActivity.getStudentId());
    private RecyclerView recyclerView;
    private int studentId = MainActivity.getStudentId();
    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        //initCourse();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        Button button = (Button) view.findViewById (R.id.click);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(context, StudentChangeActivity.class);
                intent.putExtra("id",studentId);
                startActivity(intent);

            }

        });
        Button button1 = (Button) view.findViewById (R.id.act);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(context, ActActivity.class);
                startActivity(intent);

            }

        });
        Button button2 = (Button) view.findViewById (R.id.yy);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(context,YyActivity.class);
                startActivity(intent);

            }

        });

        // Inflate the layout for this fragment
        return view;
    }
    public void onResume(){
        super.onResume();
        StudentList = student.getStudent(MainActivity.getStudentId());
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);
    }

}