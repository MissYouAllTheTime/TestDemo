package com.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.bean.UserInfo;
import com.mvp.view.RegisterView;
import com.testdemo.utils.PromptUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by XuYanping on 2017/3/29.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private Context mContext;
    private RegisterView mView;
    private EditText mUserName, mPsw, mSurePsw;
    private Button mRegister;
    private String userName, psw, surePsw;
    private UserInfo mUserInfo;

    public RegisterPresenter(Context context) {
        mContext = context;
        mUserInfo = new UserInfo();
    }

    public void initView() {
        mView = getView();
        if (mView != null) {
            mUserName = mView.getUserName();
            mPsw = mView.getPsw();
            mSurePsw = mView.getSurePsw();
            mRegister = mView.getRegister();
            mRegister.setOnClickListener(mRegisterClickListener);
        }
    }

    View.OnClickListener mRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            userName = mUserName.getText().toString().trim();
            psw = mPsw.getText().toString().trim();
            surePsw = mSurePsw.getText().toString().trim();
            if (TextUtils.isEmpty(userName) | TextUtils.isEmpty(psw) | TextUtils.isEmpty(surePsw)) {
                PromptUtil.toastShowShort(mContext, "请先完善信息");
                return;
            }
            if (!psw.equals(surePsw)) {
                PromptUtil.toastShowShort(mContext, "两次输入密码不一致，请核对后继续");
                return;
            }
            mUserInfo.setUserName(userName);
            mUserInfo.setPsw(psw);
            mUserInfo.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        PromptUtil.toastShowShort(mContext, "添加数据成功，返回objectId为：" + objectId);
                    } else {
                        PromptUtil.toastShowShort(mContext, "创建数据失败：" + e.getMessage());
                    }
                }
            });

        }
    };
}
