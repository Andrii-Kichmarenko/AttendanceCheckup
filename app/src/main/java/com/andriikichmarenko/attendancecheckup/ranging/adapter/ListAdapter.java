package com.andriikichmarenko.attendancecheckup.ranging.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andriikichmarenko.attendancecheckup.R;
import com.andriikichmarenko.attendancecheckup.model.Member;

import org.altbeacon.beacon.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class MemberViewHolder extends RecyclerView.ViewHolder{

    TextView txtName, txtAddInfo;
    MemberViewHolder(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.tv_name);
        txtAddInfo = itemView.findViewById(R.id.tv_addinfo);
    }
}

public class ListAdapter extends RecyclerView.Adapter<MemberViewHolder>{
    private LinkedHashMap<Identifier, Member> mMemberMap;
    private Context mContext;
    private LayoutInflater inflater;

    public ListAdapter(Context mContext, LinkedHashMap<Identifier, Member> memberList) {
        this.mContext = mContext;
        this.mMemberMap = memberList;
        this.mMemberMap = memberList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View memberView = inflater.inflate(R.layout.member, parent,false);
        return new MemberViewHolder(memberView);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        ArrayList<Member> memberList = new ArrayList<>(mMemberMap.values());
        holder.txtName.setText(memberList.get(position).getmName());
        holder.txtAddInfo.setText(memberList.get(position).getmInfo());
    }

    @Override
    public int getItemCount() {
        return mMemberMap.size();
    }
}
