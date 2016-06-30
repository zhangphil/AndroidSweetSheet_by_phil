package zhangphil.demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SweetSheet mSweetSheet;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl = (RelativeLayout) findViewById(R.id.root);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSweetSheet.isShow())
                    mSweetSheet.dismiss();
                else
                    mSweetSheet.show();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final ArrayList<MenuEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.iconId = R.mipmap.ic_launcher;
            menuEntity.titleColor = Color.RED;
            menuEntity.title = "zhang phil @ csdn " + i;
            list.add(menuEntity);
        }

        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(rl);

        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        //如果是从菜单中加载，那么是 .setMenuList(R.menu.menu_sweet);
        mSweetSheet.setMenuList(list);

        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));

        //根据设置不同Effect 来显示背景效果
        // BlurEffect:模糊效果.
        // DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new DimEffect(10f));

        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                //即时改变当前项的颜色
                list.get(position).titleColor = Color.GREEN;

                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();

                Toast.makeText(MainActivity.this, menuEntity.title + "  " + position, Toast.LENGTH_SHORT).show();

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mSweetSheet.isShow()) {
            mSweetSheet.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
