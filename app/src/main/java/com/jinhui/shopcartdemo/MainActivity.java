package com.jinhui.shopcartdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinhui.shopcartdemo.adapter.ShoppingCartAdapter;
import com.jinhui.shopcartdemo.bean.ShoppingBean;
import com.jinhui.shopcartdemo.bean.UpdataButton;
import com.jinhui.shopcartdemo.utils.CommomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements ShoppingCartAdapter.OnRecyclerViewItemClickListener, ShoppingCartAdapter.OnDeleteClickListener {


    @BindView(R.id.rlv_shopcart)
    RecyclerView rlvShopcart;
    @BindView(R.id.bock_return)
    ImageView bockReturn;
    @BindView(R.id.tv_shopcart_addselect)
    CheckBox tvShopcartAddselect;
    @BindView(R.id.tv_shopcart_totalprice)
    TextView tvShopcartTotalprice;
    @BindView(R.id.tv_shopcart_totalnum)
    TextView tvShopcartTotalnum;
    @BindView(R.id.tv_shopcart_submit)
    TextView tvShopcartSubmit;

    private ShoppingBean shopCartBeans;
    private List<ShoppingBean.DataBean> data;

    private ShoppingCartAdapter mallShopCartAdapter;
    // 全选是否选择
    boolean isSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        //注册事件
        EventBus.getDefault().register(this);
    }



    /**
     * 设置数据
     */
    private void initData() {
        //测试用的数据转换成实体类，赋值到adapter中
        String jsonData = "{\"status\":true,\"msg\":\"\",\"data\":[{\"store_name\":\"微超优品\",\"user_id\":\"229\",\"store_id\":\"14828331510902860000\",\"list\":[{\"goods_price\":183,\"cart_id\":\"15232812841628050000\",\"user_id\":\"229\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15162475454444720000\",\"goods_num\":\"1\",\"goods_name\":\"飞科FC5901专业发廊理发器电推剪电动推子成人老人儿童剃头\",\"goods_image\":\"http://img.lion-mall.com/goods/20180118/dbff92fc8dee4e26c39b21ee207a18a8.jpg\",\"spec_desc\":\"\",\"spec1_name\":\"型号\",\"spec1_value\":\"FC5901(积分价)\",\"spec2_name\":\"\",\"spec2_value\":\"\",\"proportion_return\":\"50\",\"goods_points\":66,\"is_have_point\":\"1\",\"model_id\":\"15162475454434460000\"}]},{\"store_name\":\"本港海产\",\"user_id\":\"3096\",\"store_id\":\"15132364355633290000\",\"list\":[{\"goods_price\":33,\"cart_id\":\"15232812763901580000\",\"user_id\":\"3096\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15156635008014120000\",\"goods_num\":\"1\",\"goods_name\":\"本港海产 即食大片海苔原味辣味125g包邮\",\"goods_image\":\"http://img.lion-mall.com/goods/20180111/65aae13eb49e93939850c707dbf69966.jpg\",\"spec_desc\":\"\",\"spec1_name\":\"规格\",\"spec1_value\":\"1*125g(积分价)\",\"spec2_name\":\"\",\"spec2_value\":\"\",\"proportion_return\":\"50\",\"goods_points\":7,\"is_have_point\":\"1\",\"model_id\":\"15156635008003640000\"}]},{\"store_name\":\"智能生活屋\",\"user_id\":\"3090\",\"store_id\":\"15110923292896270000\",\"list\":[{\"goods_price\":98,\"cart_id\":\"15232812214585490000\",\"user_id\":\"3090\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15154810744781680000\",\"goods_num\":\"1\",\"goods_name\":\"杜酷（DUKU） 无线蓝牙键盘多屏双通道蓝牙键盘通用\",\"goods_image\":\"http://img.lion-mall.com/goods/20180109/8885a44d743b75ccb0f3601686ddb719.jpg\",\"spec_desc\":\"\",\"spec1_name\":\"颜色\",\"spec1_value\":\"金色(积分价)\",\"spec2_name\":\"\",\"spec2_value\":\"\",\"proportion_return\":\"50\",\"goods_points\":31,\"is_have_point\":\"1\",\"model_id\":\"15154810744751190000\"}]},{\"store_name\":\"聚美佳品\",\"user_id\":\"2985\",\"store_id\":\"15028511939255260000\",\"list\":[{\"goods_price\":48,\"cart_id\":\"15232475994654440000\",\"user_id\":\"2985\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15153090695659930000\",\"goods_num\":\"2\",\"goods_name\":\"卡通抱枕被子两用 多功能暖手汽车空调被三合一\",\"goods_image\":\"http://img.lion-mall.com/goods/20180107/568058f3aaf01cd0297b26d75c1ff85a.png\",\"spec_desc\":\"\",\"spec1_name\":\"颜色\",\"spec1_value\":\"可爱猫\",\"spec2_name\":\"\",\"spec2_value\":\"\",\"proportion_return\":\"50\",\"goods_points\":0,\"is_have_point\":\"1\",\"model_id\":\"15153090695654650000\"}]},{\"store_name\":\"羽森家纺\",\"user_id\":\"267\",\"store_id\":\"14830180850588560000\",\"list\":[{\"goods_price\":380,\"cart_id\":\"15232475900203700000\",\"user_id\":\"267\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15155019232807690000\",\"goods_num\":\"1\",\"goods_name\":\"羽森高档双层纱贡缎阳绒四件套\",\"goods_image\":\"http://img.lion-mall.com/goods/20180109/7de0db542473faf6659ed457dda87275.jpg\",\"spec_desc\":\"\",\"spec1_name\":\"颜色\",\"spec1_value\":\"蒙特城堡（香槟灰）\",\"spec2_name\":\"规格\",\"spec2_value\":\"200*230\",\"proportion_return\":\"50\",\"goods_points\":0,\"is_have_point\":\"1\",\"model_id\":\"15155019232792440000\"}]},{\"store_name\":\"戈勒世家\",\"user_id\":\"1098\",\"store_id\":\"14879242960201500000\",\"list\":[{\"goods_price\":67,\"cart_id\":\"15232475545649120000\",\"user_id\":\"1098\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15168503821784490000\",\"goods_num\":\"2\",\"goods_name\":\"男士特色独眼怪兽胸包单肩包斜挎包时尚户外休闲包潮流男包\",\"goods_image\":\"http://img.lion-mall.com/goods/20180125/a355d78946c721565d2e44687f15f934.jpg\",\"spec_desc\":\"\",\"spec1_name\":\"颜色\",\"spec1_value\":\"黑色(积分兑)\",\"spec2_name\":\"\",\"spec2_value\":\"\",\"proportion_return\":\"50\",\"goods_points\":12,\"is_have_point\":\"1\",\"model_id\":\"15168503821773910000\"},{\"goods_price\":82,\"cart_id\":\"15232475436635450000\",\"user_id\":\"1098\",\"member_id\":\"15222940696559300000\",\"goods_id\":\"15168478480459580000\",\"goods_num\":\"1\",\"goods_name\":\"男士手提包AD18单肩包斜挎皮包商务公文包手提包\",\"goods_image\":\"http://img.lion-mall.com/goods/20180125/31966da605420f672712bd07a36dcd4a.jpg\",\"spec_desc\":\"\",\"spec1_name\":\"颜色\",\"spec1_value\":\"黑色(积分兑)\",\"spec2_name\":\"\",\"spec2_value\":\"\",\"proportion_return\":\"50\",\"goods_points\":23,\"is_have_point\":\"1\",\"model_id\":\"15168478480445350000\"}]}]}";
        Gson gson = new Gson();
        shopCartBeans = gson.fromJson(jsonData, ShoppingBean.class);
        data = null;
        data = shopCartBeans.getData();
        Log.e("获得的数据" , data.toString());
        if (mallShopCartAdapter != null) {
            Log.e("方法 1" , data.toString());  //不走里面的方法哦，走下面的方法
            mallShopCartAdapter.setmDatas(data);
            mallShopCartAdapter.notifyDataSetChanged();
        } else {
            Log.e("方法 2" , data.toString());
            mallShopCartAdapter = new ShoppingCartAdapter(this, data);
            // 设置适配器
            rlvShopcart.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
            rlvShopcart.setAdapter(mallShopCartAdapter);
            // 设置监听
            mallShopCartAdapter.setOnItemClickListener(this);
            mallShopCartAdapter.setOnDeleteClickListener(this); //删除的点击
        }
    }


    @Override
    public void onItemClick(View view, ShoppingBean.DataBean data) {

    }

    //这里用了eventBus来进行实时价格的UI更改。
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(UpdataButton event) {
        //刷新UI
        tvShopcartTotalprice.setText("结算(￥" + event.getDiscribe() + ")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




    @OnClick({R.id.bock_return, R.id.tv_shopcart_addselect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bock_return:
                // 返回
                this.finish();
                break;
            case R.id.tv_shopcart_addselect:
                // 全选
                if (isSelect) {
                    tvShopcartAddselect.setChecked(false);
                    isSelect = false;
                    mallShopCartAdapter.setAllselect(false);
                } else {
                    tvShopcartAddselect.setChecked(true);
                    isSelect = true;
                    mallShopCartAdapter.setAllselect(true);
                }
                break;
        }
    }

    /**
     * 删除的点击事件
     *
     * @param view
     * @param id
     */
    @Override
    public void onDeleteClick(View view, String id) {
        new CommomDialog(MainActivity.this, R.style.dialog, "", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Toast.makeText(MainActivity.this, "被删除了,调用删除接口", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        }).setTitle("你确定要删除吗？").show();
    }
}
